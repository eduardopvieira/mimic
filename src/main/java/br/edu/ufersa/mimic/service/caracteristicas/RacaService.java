package br.edu.ufersa.mimic.service;

import br.edu.ufersa.mimic.dto.RacaDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.Traco;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.TracoRepository;
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
    private final TracoRepository tracoRepository;

    @Autowired
    public RacaService(RacaRepository racaRepository, TracoRepository tracoRepository) {
        this.racaRepository = racaRepository;
        this.tracoRepository = tracoRepository;
    }

    @Transactional
    public RacaDTO salvar(RacaDTO dto) {
        racaRepository.findByNome(dto.getNome()).ifPresent(r -> {
            throw new IllegalArgumentException("Uma raça com o nome '" + dto.getNome() + "' já existe.");
        });

        Raca raca = new Raca();
        mapearDtoParaEntidade(dto, raca);

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
    public RacaDTO atualizar(Long id, RacaDTO dto) {
        Raca racaExistente = racaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Raça não encontrada com id: " + id));

        mapearDtoParaEntidade(dto, racaExistente);

        Raca racaAtualizada = racaRepository.save(racaExistente);
        return new RacaDTO(racaAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!racaRepository.existsById(id)) {
            throw new EntityNotFoundException("Raça não encontrada com id: " + id);
        }
        racaRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(RacaDTO dto, Raca raca) {
        raca.setNome(dto.getNome());
        raca.setDeslocamento(dto.getDeslocamento());
        raca.setTamanho(dto.getTamanho());

        if (dto.getTracosIds() != null && !dto.getTracosIds().isEmpty()) {
            List<Traco> tracos = tracoRepository.findAllById(dto.getTracosIds());
            if(tracos.size() != dto.getTracosIds().size()){
                throw new EntityNotFoundException("Um ou mais traços não foram encontrados.");
            }
            raca.setTracos(tracos);
        } else {
            raca.setTracos(Collections.emptyList());
        }
    }
}
