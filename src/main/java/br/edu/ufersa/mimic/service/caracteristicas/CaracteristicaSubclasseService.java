package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.CaracteristicaSubclasseDTO;
import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaSubclasse;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.CaracteristicaSubclasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaracteristicaSubclasseService {

    private final CaracteristicaSubclasseRepository caracteristicaRepository;
    private final SubclasseRepository subclasseRepository;

    @Autowired
    public CaracteristicaSubclasseService(CaracteristicaSubclasseRepository caracteristicaRepository, SubclasseRepository subclasseRepository) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.subclasseRepository = subclasseRepository;
    }

    @Transactional
    public CaracteristicaSubclasseDTO salvar(CaracteristicaSubclasseDTO dto) {
        Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + dto.getSubclasseId()));

        CaracteristicaSubclasse novaCaracteristica = new CaracteristicaSubclasse();
        novaCaracteristica.setNome(dto.getNome());
        novaCaracteristica.setDescricao(dto.getDescricao());
        novaCaracteristica.setNivelAdquirido(dto.getNivelAdquirido());
        novaCaracteristica.setSubclasse(subclasse);

        CaracteristicaSubclasse caracteristicaSalva = caracteristicaRepository.save(novaCaracteristica);
        return new CaracteristicaSubclasseDTO(caracteristicaSalva);
    }

    @Transactional(readOnly = true)
    public CaracteristicaSubclasseDTO buscarPorId(Long id) {
        return caracteristicaRepository.findById(id)
                .map(CaracteristicaSubclasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Característica de Subclasse não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CaracteristicaSubclasseDTO> listarPorSubclasse(Long subclasseId) {
        Subclasse subclasse = subclasseRepository.findById(subclasseId)
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + subclasseId));

        return caracteristicaRepository.findBySubclasse(subclasse).stream()
                .map(CaracteristicaSubclasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CaracteristicaSubclasseDTO atualizar(Long id, CaracteristicaSubclasseDTO dto) {
        CaracteristicaSubclasse caracteristicaExistente = caracteristicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Característica de Subclasse não encontrada com id: " + id));

        Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + dto.getSubclasseId()));

        caracteristicaExistente.setNome(dto.getNome());
        caracteristicaExistente.setDescricao(dto.getDescricao());
        caracteristicaExistente.setNivelAdquirido(dto.getNivelAdquirido());
        caracteristicaExistente.setSubclasse(subclasse);

        CaracteristicaSubclasse caracteristicaAtualizada = caracteristicaRepository.save(caracteristicaExistente);
        return new CaracteristicaSubclasseDTO(caracteristicaAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!caracteristicaRepository.existsById(id)) {
            throw new EntityNotFoundException("Característica de Subclasse não encontrada com id: " + id);
        }
        caracteristicaRepository.deleteById(id);
    }
}
