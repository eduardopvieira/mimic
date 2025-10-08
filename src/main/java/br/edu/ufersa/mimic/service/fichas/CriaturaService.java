package br.edu.ufersa.mimic.service.fichas;

import br.edu.ufersa.mimic.dto.fichas.CriaturaDTO;
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
        if (!criaturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Criatura não encontrada com id: " + id);
        }
        dto.setId(id);
        Criatura criaturaParaAtualizar = new Criatura(dto);
        return new CriaturaDTO(criaturaRepository.save(criaturaParaAtualizar));
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!criaturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Criatura não encontrada com id: " + id);
        }
        criaturaRepository.deleteById(id);
    }
}
