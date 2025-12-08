package br.edu.ufersa.mimic.service.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.HabilidadeCriaturaDTO;
import br.edu.ufersa.mimic.repository.habilidades.HabilidadeCriaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabilidadeCriaturaService {

    private final HabilidadeCriaturaRepository hcRepository;

    @Autowired
    public HabilidadeCriaturaService(HabilidadeCriaturaRepository hcRepository) {
        this.hcRepository = hcRepository;
    }

    @Transactional(readOnly = true)
    public List<HabilidadeCriaturaDTO> listarTodasHabilidadesCriaturas() {
        return hcRepository.findAll().stream()
                .map(HabilidadeCriaturaDTO::new)
                .collect(Collectors.toList());
    }

}