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
        // Como você decidiu que Talentos são "apenas do livro", usamos findAll simples
        return acRepository.findAll().stream()
                .map(AcaoCriaturaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AcaoCriaturaDTO buscarAcaoCriaturaPorId(Long id) {
        return acRepository.findById(id)
                .map(AcaoCriaturaDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + id));
    }
}