package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.ClasseDTO;
import br.edu.ufersa.mimic.service.caracteristicas.ClasseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping
    public ResponseEntity<List<ClasseDTO>> listarTodasClasses() {
        return ResponseEntity.ok(classeService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> buscarClassePorId(@PathVariable Long id) {
        return ResponseEntity.ok(classeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClasseDTO> criarClasse(@Valid @RequestBody ClasseDTO dto) {
        ClasseDTO novaClasse = classeService.salvar(dto);
        return new ResponseEntity<>(novaClasse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseDTO> atualizarClasse(@PathVariable Long id, @Valid @RequestBody ClasseDTO dto) {
        return ResponseEntity.ok(classeService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarClasse(@PathVariable Long id) {
        classeService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
