package br.edu.ufersa.mimic.api.controller.geral;

import br.edu.ufersa.mimic.api.dto.caracteristicas.ClasseDTO;
import br.edu.ufersa.mimic.api.dto.caracteristicas.SubclasseDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.repository.caracteristicas.ClasseRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubclasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private SubclasseRepository subclasseRepository;

    @GetMapping
    public ResponseEntity<List<ClasseDTO>> listarClasses() {
        List<Classe> racas = classeRepository.findAll();
        List<ClasseDTO> dtos = racas.stream().map(ClasseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{classeId}/subclasses")
    public ResponseEntity<List<SubclasseDTO>> listarSubclassesPorClasse(@PathVariable Long classeId) {

        List<SubclasseDTO> subclasses = subclasseRepository.findByClassePaiId(classeId)
                .stream()
                .map(SubclasseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(subclasses);
    }
}