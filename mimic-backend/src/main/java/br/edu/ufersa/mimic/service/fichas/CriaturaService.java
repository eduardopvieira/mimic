package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import br.edu.ufersa.mimic.model.habilidades.HabilidadeCriatura;
import br.edu.ufersa.mimic.repository.fichas.CriaturaRepository;
import br.edu.ufersa.mimic.repository.habilidades.AcaoCriaturaRepository;
import br.edu.ufersa.mimic.repository.habilidades.HabilidadeCriaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriaturaService {

    @Autowired private CriaturaRepository criaturaRepository;
    @Autowired private HabilidadeCriaturaRepository habilidadeRepository;
    @Autowired private AcaoCriaturaRepository acaoRepository;

    @Transactional
    public CriaturaDTO salvar(CriaturaDTO dto, Long usuarioId) {
        Criatura criatura = new Criatura();

        Usuario u = new Usuario();
        u.setUsuarioId(usuarioId);
        criatura.setUsuario(u);

        mapearDtoParaEntidade(dto, criatura);

        return new CriaturaDTO(criaturaRepository.save(criatura));
    }

    @Transactional
    public CriaturaDTO atualizar(Long id, CriaturaDTO dto, Long usuarioId) {
        Criatura criatura = criaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada."));

        if (!criatura.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new SecurityException("Você não tem permissão para alterar esta criatura.");
        }

        mapearDtoParaEntidade(dto, criatura);

        return new CriaturaDTO(criaturaRepository.save(criatura));
    }

    @Transactional(readOnly = true)
    public CriaturaDTO buscarPorId(Long id, Long usuarioId) {
        Criatura criatura = criaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada."));

        if (!criatura.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new SecurityException("Você não tem permissão para acessar esta criatura.");
        }

        return new CriaturaDTO(criatura);
    }

    @Transactional
    public void salvarImagem(Long criaturaId, Long usuarioId, MultipartFile file) throws IOException {
        Criatura criatura = criaturaRepository.findByIdAndUsuario_UsuarioId(criaturaId, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada."));

        criatura.setImagem(file.getBytes());
        criaturaRepository.save(criatura);
    }

    @Transactional
    public void deletar(Long id, Long usuarioId) {
        Criatura criatura = criaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada."));

        if (!criatura.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new SecurityException("Você não tem permissão para deletar esta criatura.");
        }

        criaturaRepository.delete(criatura);
    }

    @Transactional(readOnly = true)
    public List<CriaturaDTO> listarPorUsuario(Long usuarioId) {
        return criaturaRepository.findByUsuario_UsuarioId(usuarioId).stream()
                .map(CriaturaDTO::new)
                .collect(Collectors.toList());
    }

    private void mapearDtoParaEntidade(CriaturaDTO dto, Criatura c) {
        c.setNome(dto.getNome());
        c.setTipo(dto.getTipo());
        c.setTag(dto.getTag());

        c.setTamanho(dto.getTamanho());
        c.setAlinhamento(dto.getAlinhamento());

        c.setCa(dto.getCa());
        c.setPv(dto.getPv());

        c.setDeslBase(dto.getDeslBase());
        c.setDeslVoo(dto.getDeslVoo());
        c.setDeslNatacao(dto.getDeslNatacao());

        c.setForca(dto.getStr());
        c.setDestreza(dto.getDex());
        c.setConstituicao(dto.getCon());
        c.setInteligencia(dto.getIntelligence());
        c.setSabedoria(dto.getWis());
        c.setCarisma(dto.getCha());

        c.setSalvaguardas(dto.getSaves());
        c.setPericias(dto.getSkills());
        c.setResistencias(dto.getResistDano());
        c.setImunidades(dto.getImunidDano());
        c.setImunidadesCondicao(dto.getImunidCond());
        c.setSentidos(dto.getSentidos());
        c.setIdiomas(dto.getIdiomas());
        c.setNd(dto.getNd());

        c.setAcoesLendarias(dto.getLegendaryActions());
        c.setAcoesCovil(dto.getLairActions());

        if (dto.getHabilidadesIds() != null) {
            List<HabilidadeCriatura> habs = habilidadeRepository.findAllById(dto.getHabilidadesIds());
            c.setHabilidades(habs);
        }
        if (dto.getAcoesIds() != null) {
            List<AcaoCriatura> acoes = acaoRepository.findAllById(dto.getAcoesIds());
            c.setAcoes(acoes);
        }
    }
}