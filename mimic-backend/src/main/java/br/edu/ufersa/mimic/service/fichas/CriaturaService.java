package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
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

        // Reutilizamos o método de mapeamento para atualizar os dados
        mapearDtoParaEntidade(dto, criatura);

        return new CriaturaDTO(criaturaRepository.save(criatura));
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

        // Conversão de Enums (Frontend manda string customizada, precisamos mapear ou usar padrão)
        // Exemplo simples: Tentar valueOf uppercase, se falhar, null.
        try { c.setTamanho(Tamanho.valueOf(dto.getTamanho().toUpperCase())); } catch (Exception e) { c.setTamanho(Tamanho.MEDIO); }

        // Mapeamento manual do Alinhamento (O front manda "Leal e Bom", o Enum é LEAL_BOM)
        // Idealmente faça um switch ou mapa auxiliar.
        c.setAlinhamento(null); // TODO: Implementar conversor String -> Enum

        c.setCa(dto.getCa());
        c.setPv(dto.getPv());

        // Junta os deslocamentos numa string só
        String desl = "Base " + dto.getDeslBase();
        if(dto.getDeslVoo() != null && !dto.getDeslVoo().isEmpty()) desl += ", Voo " + dto.getDeslVoo();
        if(dto.getDeslNatacao() != null && !dto.getDeslNatacao().isEmpty()) desl += ", Natação " + dto.getDeslNatacao();
        c.setDeslocamento(desl);

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

        // Listas
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