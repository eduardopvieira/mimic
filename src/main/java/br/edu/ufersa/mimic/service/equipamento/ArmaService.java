package br.edu.ufersa.mimic.service.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaDTO;
import br.edu.ufersa.mimic.dto.equipamento.ArmaduraDTO;
import br.edu.ufersa.mimic.model.equipamento.Arma;
import br.edu.ufersa.mimic.model.equipamento.Armadura;
import br.edu.ufersa.mimic.repository.equipamento.ArmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmaService {

    private final ArmaRepository armaRepository;

    @Autowired
    public ArmaService(ArmaRepository armaRepository) {
        this.armaRepository = armaRepository;
    }

    @Transactional
    public ArmaDTO salvar(ArmaDTO dto) {
        armaRepository.findByNome(dto.getNome()).ifPresent(a -> {
            throw new IllegalArgumentException("Uma arma com o nome '" + dto.getNome() + "' já existe.");
        });

        Arma arma = new Arma(dto); // Assumindo que você adicionou o construtor à entidade
        Arma armaSalva = armaRepository.save(arma);
        return new ArmaDTO(armaSalva);
    }

    @Transactional(readOnly = true)
    public List<ArmaDTO> listarTodas() {
        return armaRepository.findAll().stream()
                .map(ArmaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArmaDTO buscarPorId(Long id) {
        return armaRepository.findById(id)
                .map(ArmaDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Arma não encontrada com id: " + id));
    }

    @Transactional
    public ArmaDTO atualizar(Long id, ArmaDTO dto) {
        if (!armaRepository.existsById(id)) {
            throw new EntityNotFoundException("Arma não encontrada com id: " + id);
        }
        dto.setId(id);
        Arma armaParaAtualizar = new Arma(dto);
        Arma armaAtualizada = armaRepository.save(armaParaAtualizar);
        return new ArmaDTO(armaAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!armaRepository.existsById(id)) {
            throw new EntityNotFoundException("Arma não encontrada com id: " + id);
        }
        armaRepository.deleteById(id);
    }
}
