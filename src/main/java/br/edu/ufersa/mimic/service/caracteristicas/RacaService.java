package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.RacaDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.TracoRacialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RacaService {

    private final RacaRepository racaRepository;
    private final TracoRacialRepository tracoRacialRepository;

    @Autowired
    public RacaService(RacaRepository racaRepository, TracoRacialRepository tracoRacialRepository) {
        this.racaRepository = racaRepository;
        this.tracoRacialRepository = tracoRacialRepository;
    }

    @Transactional
    public RacaDTO atualizar(Long id, RacaDTO dto) {
        if (!racaRepository.existsById(id)) {
            throw new EntityNotFoundException("Raça não encontrada com o id: " + id);
        }
        dto.setId(id);
        Raca racaParaAtualizar = new Raca(dto);
        Raca racaAtualizada = racaRepository.save(racaParaAtualizar);
        return new RacaDTO(racaAtualizada);
    }

    @Transactional
    public RacaDTO salvar(RacaDTO dto) {
        racaRepository.findByNome(dto.getNome()).ifPresent(r -> {
            throw new IllegalArgumentException("Uma raça com o nome '" + dto.getNome() + "' já existe.");
        });

        Raca raca = new Raca(dto);

        Raca racaSalva = racaRepository.save(raca);
        return new RacaDTO(racaSalva);
    }

    @Transactional(readOnly = true)
    public RacaDTO buscarPorId(Long id) {
        Raca raca = racaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com id: " + id));
        return new RacaDTO(raca);
    }

    @Transactional(readOnly = true)
    public List<RacaDTO> listarTodas() {
        return racaRepository.findAll().stream()
                .map(RacaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!racaRepository.existsById(id)) {
            throw new EntityNotFoundException("Raça não encontrada com id: " + id);
        }
        racaRepository.deleteById(id);
    }

}
