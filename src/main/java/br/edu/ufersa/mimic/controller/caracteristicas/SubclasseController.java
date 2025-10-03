package br.edu.ufersa.mimic.controller;

import br.edu.ufersa.mimic.dto.caracteristicas.SubclasseDTO;
import br.edu.ufersa.mimic.service.caracteristicas.SubclasseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subclasses")
public class SubclasseController {

    private final SubclasseService subclasseService;

    @Autowired
    public SubclasseController(SubclasseService subclasseService) {
        this.subclasseService = subclasseService;
    }

    @PostMapping
    public ResponseEntity<SubclasseDTO> criarSubclasse(@Valid @RequestBody SubclasseDTO dto) {
        return new ResponseEntity<>(subclasseService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubclasseDTO> buscarSubclassePorId(@PathVariable Long id) {
        return ResponseEntity.ok(subclasseService.buscarPorId(id));
    }

    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<SubclasseDTO>> listarSubclassesPorClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(subclasseService.listarPorClasse(classeId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubclasseDTO> atualizarSubclasse(@PathVariable Long id, @Valid @RequestBody SubclasseDTO dto) {
        return ResponseEntity.ok(subclasseService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSubclasse(@PathVariable Long id) {
        subclasseService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
