package br.edu.ufersa.mimic.service;

import br.edu.ufersa.mimic.dto.ClasseDTO;
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
        return classeRepository.findById(id)
                .map(ClasseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + id));
    }

    @Transactional
    public ClasseDTO salvar(ClasseDTO dto) {
        classeRepository.findByNome(dto.getNome()).ifPresent(c -> {
            throw new IllegalArgumentException("Uma classe com o nome '" + dto.getNome() + "' já existe.");
        });

        Classe classe = new Classe();
        classe.setNome(dto.getNome());
        classe.setDescricao(dto.getDescricao());
        classe.setDadoDeVida(dto.getDadoDeVida());
        classe.setProficienciasArmaduras(dto.getProficienciasArmaduras());
        classe.setProficienciasArmas(dto.getProficienciasArmas());
        classe.setProficienciasTestesDeResistencia(dto.getProficienciasTestesDeResistencia());
        classe.setOpcoesDePericias(dto.getOpcoesDePericias());
        classe.setQuantidadeEscolhaPericias(dto.getQuantidadeEscolhaPericias());
        classe.setConjurador(dto.isConjurador());
        classe.setAtributoDeConjuracao(dto.getAtributoDeConjuracao());

        Classe classeSalva = classeRepository.save(classe);
        return new ClasseDTO(classeSalva);
    }

    @Transactional
    public ClasseDTO atualizar(Long id, ClasseDTO dto) {
        Classe classeExistente = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe não encontrada com o id: " + id));

        classeExistente.setNome(dto.getNome());
        classeExistente.setDescricao(dto.getDescricao());
        classeExistente.setDadoDeVida(dto.getDadoDeVida());
        classeExistente.setProficienciasArmaduras(dto.getProficienciasArmaduras());
        classeExistente.setProficienciasArmas(dto.getProficienciasArmas());
        classeExistente.setProficienciasTestesDeResistencia(dto.getProficienciasTestesDeResistencia());
        classeExistente.setOpcoesDePericias(dto.getOpcoesDePericias());
        classeExistente.setQuantidadeEscolhaPericias(dto.getQuantidadeEscolhaPericias());
        classeExistente.setConjurador(dto.isConjurador());
        classeExistente.setAtributoDeConjuracao(dto.getAtributoDeConjuracao());

        Classe classeAtualizada = classeRepository.save(classeExistente);
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
