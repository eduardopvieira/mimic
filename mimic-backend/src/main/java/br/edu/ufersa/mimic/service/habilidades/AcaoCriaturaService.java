package br.edu.ufersa.mimic.service.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.AcaoCriaturaDTO;
import br.edu.ufersa.mimic.repository.habilidades.AcaoCriaturaRepository;
import br.edu.ufersa.mimic.repository.habilidades.HabilidadeCriaturaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcaoCriaturaService {

    private final AcaoCriaturaRepository acRepository;

    @Autowired
    public AcaoCriaturaService(AcaoCriaturaRepository acRepository) {
        this.acRepository = acRepository;
    }

    @Transactional(readOnly = true)
    public List<AcaoCriaturaDTO> listarTodasAcoesCriaturas() {
        return acRepository.findAll().stream()
                .map(AcaoCriaturaDTO::new)
                .collect(Collectors.toList());
    }

}