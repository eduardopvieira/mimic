package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.ClasseDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    @Autowired
    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Transactional(readOnly = true)
    public List<ClasseDTO> listarTodas() {
        return classeRepository.findAll().stream()
                .map(ClasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClasseDTO buscarPorId(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + id));
        return new ClasseDTO(classe);
    }

    @Transactional
    public ClasseDTO salvar(ClasseDTO dto) {
        classeRepository.findByNome(dto.getNome()).ifPresent(c -> {
            throw new IllegalArgumentException("Uma classe com o nome '" + dto.getNome() + "' já existe.");
        });

        Classe classe = new Classe(dto);

        Classe classeSalva = classeRepository.save(classe);
        return new ClasseDTO(classeSalva);
    }

    @Transactional
    public ClasseDTO atualizar(Long id, ClasseDTO dto) {
        if (!classeRepository.existsById(id)) {
            throw new EntityNotFoundException("Classe não encontrada com o id: " + id);
        }
        dto.setId(id);
        Classe classeParaAtualizar = new Classe(dto);
        Classe classeAtualizada = classeRepository.save(classeParaAtualizar);
        return new ClasseDTO(classeAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!classeRepository.existsById(id)) {
            throw new EntityNotFoundException("Classe não encontrada com o id: " + id);
        }
        classeRepository.deleteById(id);
    }

}
