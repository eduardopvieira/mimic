package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.SubclasseDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubclasseService {

    private final SubclasseRepository subclasseRepository;
    private final ClasseRepository classeRepository;

    @Autowired
    public SubclasseService(SubclasseRepository subclasseRepository, ClasseRepository classeRepository) {
        this.subclasseRepository = subclasseRepository;
        this.classeRepository = classeRepository;
    }

    @Transactional
    public SubclasseDTO salvar(SubclasseDTO dto) {
        subclasseRepository.findByNome(dto.getNome()).ifPresent(s -> {
            throw new IllegalArgumentException("Uma subclasse com o nome '" + dto.getNome() + "' já existe.");
        });

        Classe classePai = classeRepository.findById(dto.getClassePaiId())
                .orElseThrow(() -> new EntityNotFoundException("Classe pai não encontrada com id: " + dto.getClassePaiId()));

        Subclasse novaSubclasse = new Subclasse();
        novaSubclasse.setNome(dto.getNome());
        novaSubclasse.setDescricao(dto.getDescricao());
        novaSubclasse.setClassePai(classePai);

        Subclasse subclasseSalva = subclasseRepository.save(novaSubclasse);
        return new SubclasseDTO(subclasseSalva);
    }

    @Transactional(readOnly = true)
    public SubclasseDTO buscarPorId(Long id) {
        return subclasseRepository.findById(id)
                .map(SubclasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<SubclasseDTO> listarPorClasse(Long classeId) {
        Classe classePai = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException("Classe pai não encontrada com id: " + classeId));

        return subclasseRepository.findByClassePai(classePai).stream()
                .map(SubclasseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubclasseDTO atualizar(Long id, SubclasseDTO dto) {
        Subclasse subclasseExistente = subclasseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subclasse não encontrada com id: " + id));

        Classe classePai = classeRepository.findById(dto.getClassePaiId())
                .orElseThrow(() -> new EntityNotFoundException("Classe pai não encontrada com id: " + dto.getClassePaiId()));

        subclasseExistente.setNome(dto.getNome());
        subclasseExistente.setDescricao(dto.getDescricao());
        subclasseExistente.setClassePai(classePai);

        Subclasse subclasseAtualizada = subclasseRepository.save(subclasseExistente);
        return new SubclasseDTO(subclasseAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!subclasseRepository.existsById(id)) {
            throw new EntityNotFoundException("Subclasse não encontrada com id: " + id);
        }
        subclasseRepository.deleteById(id);
    }
}
