package br.edu.ufersa.mimic.service.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaduraDTO;
import br.edu.ufersa.mimic.model.equipamento.Armadura;
import br.edu.ufersa.mimic.repository.equipamento.ArmaduraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmaduraService {

    private final ArmaduraRepository armaduraRepository;

    @Autowired
    public ArmaduraService(ArmaduraRepository armaduraRepository) {
        this.armaduraRepository = armaduraRepository;
    }

    @Transactional
    public ArmaduraDTO salvar(ArmaduraDTO dto) {
        armaduraRepository.findByNome(dto.getNome()).ifPresent(a -> {
            throw new IllegalArgumentException("Uma armadura com o nome '" + dto.getNome() + "' já existe.");
        });
        Armadura armadura = new Armadura(dto);
        return new ArmaduraDTO(armaduraRepository.save(armadura));
    }

    @Transactional(readOnly = true)
    public List<ArmaduraDTO> listarTodas() {
        return armaduraRepository.findAll().stream().map(ArmaduraDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArmaduraDTO buscarPorId(Long id) {
        return armaduraRepository.findById(id).map(ArmaduraDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Armadura não encontrada com id: " + id));
    }

    @Transactional
    public ArmaduraDTO atualizar(Long id, ArmaduraDTO dto) {
        if (!armaduraRepository.existsById(id)) {
            throw new EntityNotFoundException("Armadura não encontrada com id: " + id);
        }
        dto.setId(id);
        Armadura armaduraParaAtualizar = new Armadura(dto);
        Armadura armaduraAtualizada = armaduraRepository.save(armaduraParaAtualizar);
        return new ArmaduraDTO(armaduraAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!armaduraRepository.existsById(id)) {
            throw new EntityNotFoundException("Armadura não encontrada com id: " + id);
        }
        armaduraRepository.deleteById(id);
    }
}
