package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import br.edu.ufersa.mimic.repository.fichas.CriaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriaturaService {

    private final CriaturaRepository criaturaRepository;

    @Autowired
    public CriaturaService(CriaturaRepository criaturaRepository) {
        this.criaturaRepository = criaturaRepository;
    }

    @Transactional
    public CriaturaDTO salvar(CriaturaDTO dto) {
        criaturaRepository.findByNome(dto.getNome()).ifPresent(c -> {
            throw new IllegalArgumentException("Uma criatura com o nome '" + dto.getNome() + "' já existe.");
        });
        Criatura criatura = new Criatura(dto);
        return new CriaturaDTO(criaturaRepository.save(criatura));
    }

    @Transactional(readOnly = true)
    public List<CriaturaDTO> listarTodas() {
        return criaturaRepository.findAll().stream().map(CriaturaDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CriaturaDTO buscarPorId(Long id) {
        return criaturaRepository.findById(id).map(CriaturaDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada com id: " + id));
    }

    @Transactional
    public CriaturaDTO atualizar(Long id, CriaturaDTO dto) {
        Criatura criaturaExistente = criaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Criatura não encontrada com id: " + id));

        criaturaExistente.setNome(dto.getNome());
        criaturaExistente.setTamanho(dto.getTamanho());
        criaturaExistente.setTipo(dto.getTipo());
        criaturaExistente.setAlinhamento(dto.getAlinhamento());
        criaturaExistente.setClasseDeArmadura(dto.getClasseDeArmadura());
        criaturaExistente.setPontosDeVida(dto.getPontosDeVida());
        criaturaExistente.setDadosDeVida(dto.getDadosDeVida());
        criaturaExistente.setDeslocamento(dto.getDeslocamento());
        criaturaExistente.setForca(dto.getForca());
        criaturaExistente.setDestreza(dto.getDestreza());
        criaturaExistente.setConstituicao(dto.getConstituicao());
        criaturaExistente.setInteligencia(dto.getInteligencia());
        criaturaExistente.setSabedoria(dto.getSabedoria());
        criaturaExistente.setCarisma(dto.getCarisma());
        criaturaExistente.setPericias(dto.getPericias());
        criaturaExistente.setImunidadesDano(dto.getImunidadesDano());
        criaturaExistente.setResistenciasDano(dto.getResistenciasDano());
        criaturaExistente.setVulnerabilidadesDano(dto.getVulnerabilidadesDano());
        criaturaExistente.setSentidos(dto.getSentidos());
        criaturaExistente.setIdiomas(dto.getIdiomas());
        criaturaExistente.setNivelDeDesafio(dto.getNivelDeDesafio());
        criaturaExistente.setTracosEspeciais(dto.getTracosEspeciais());
        criaturaExistente.setAcoes(dto.getAcoes());
        criaturaExistente.setAcoesBonus(dto.getAcoesBonus());
        criaturaExistente.setReacoes(dto.getReacoes());

        Criatura criaturaAtualizada = criaturaRepository.save(criaturaExistente);

        return new CriaturaDTO(criaturaAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!criaturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Criatura não encontrada com id: " + id);
        }
        criaturaRepository.deleteById(id);
    }
}
