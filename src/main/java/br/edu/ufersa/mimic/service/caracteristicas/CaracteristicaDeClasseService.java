package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.CaracteristicaDeClasseDTO;
import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import br.edu.ufersa.mimic.repository.caracteristicas.CaracteristicasDeClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.TracoRacialRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaracteristicaDeClasseService {

    private final CaracteristicasDeClasseRepository caracteristicaRepository;
    private final ClasseRepository classeRepository;
    private final SubclasseRepository subclasseRepository;
    private final TracoRacialRepository tracoRacialRepository;

    @Autowired
    public CaracteristicaDeClasseService(
            CaracteristicasDeClasseRepository caracteristicaRepository,
            ClasseRepository classeRepository,
            SubclasseRepository subclasseRepository,
            TracoRacialRepository tracoRacialRepository
    ) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.classeRepository = classeRepository;
        this.subclasseRepository = subclasseRepository;
        this.tracoRacialRepository = tracoRacialRepository;
    }

    @Transactional(readOnly = true)
    public CaracteristicaDeClasseDTO buscarPorId(Long id) {
        return caracteristicaRepository.findById(id)
                .map(CaracteristicaDeClasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Característica não encontrada com o id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CaracteristicaDeClasseDTO> listarPorClasse(Long classeId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + classeId));
        return caracteristicaRepository.findByClasse(classe).stream()
                .map(CaracteristicaDeClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CaracteristicaDeClasseDTO> listarPorSubclasse(Long subclasseId) {
        Subclasse subclasse = subclasseRepository.findById(subclasseId)
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com o id: " + subclasseId));
        return caracteristicaRepository.findBySubclasse(subclasse).stream()
                .map(CaracteristicaDeClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CaracteristicaDeClasseDTO salvar(CaracteristicaDeClasseDTO dto) {
        validarRelacionamento(dto);

        TracoRacial tracoRacial = tracoRacialRepository.findById(dto.getTracoId())
                .orElseThrow(() -> new EntityNotFoundException("Traço não encontrado com o id: " + dto.getTracoId()));

        CaracteristicaDeClasse novaCaracteristica = new CaracteristicaDeClasse();
        novaCaracteristica.setNivelAdquirido(dto.getNivelAdquirido());

        if (dto.getClasseId() != null) {
            Classe classe = classeRepository.findById(dto.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + dto.getClasseId()));
            novaCaracteristica.setClasse(classe);
        } else {
            Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com o id: " + dto.getSubclasseId()));
            novaCaracteristica.setSubclasse(subclasse);
        }

        CaracteristicaDeClasse caracteristicaSalva = caracteristicaRepository.save(novaCaracteristica);
        return new CaracteristicaDeClasseDTO(caracteristicaSalva);
    }

    @Transactional
    public CaracteristicaDeClasseDTO atualizar(Long id, CaracteristicaDeClasseDTO dto) {
        validarRelacionamento(dto);

        CaracteristicaDeClasse caracteristicaExistente = caracteristicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Característica não encontrada com o id: " + id));

        TracoRacial tracoRacial = tracoRacialRepository.findById(dto.getTracoId())
                .orElseThrow(() -> new EntityNotFoundException("Traço não encontrado com o id: " + dto.getTracoId()));

        caracteristicaExistente.setNivelAdquirido(dto.getNivelAdquirido());

        if (dto.getClasseId() != null) {
            Classe classe = classeRepository.findById(dto.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + dto.getClasseId()));
            caracteristicaExistente.setClasse(classe);
            caracteristicaExistente.setSubclasse(null);
        } else {
            Subclasse subclasse = subclasseRepository.findById(dto.getSubclasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com o id: " + dto.getSubclasseId()));
            caracteristicaExistente.setSubclasse(subclasse);
            caracteristicaExistente.setClasse(null);
        }

        CaracteristicaDeClasse caracteristicaAtualizada = caracteristicaRepository.save(caracteristicaExistente);
        return new CaracteristicaDeClasseDTO(caracteristicaAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!caracteristicaRepository.existsById(id)) {
            throw new EntityNotFoundException("Característica não encontrada com o id: " + id);
        }
        caracteristicaRepository.deleteById(id);
    }

    private void validarRelacionamento(CaracteristicaDeClasseDTO dto) {
        boolean classePresente = dto.getClasseId() != null;
        boolean subclassePresente = dto.getSubclasseId() != null;

        if (classePresente == subclassePresente) { // true==true ou false==false
            throw new IllegalArgumentException("A característica deve pertencer a uma classe OU a uma subclasse, mas não a ambas ou a nenhuma.");
        }
    }
}
