package br.edu.ufersa.mimic.controller.fichas;

import br.edu.ufersa.mimic.dto.PersonagemDTO;
import br.edu.ufersa.mimic.service.fichas.PersonagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemService personagemService;

    @Autowired
    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }

    @PostMapping
    public ResponseEntity<PersonagemDTO> criarPersonagem(@Valid @RequestBody PersonagemDTO dto) {
        return new ResponseEntity<>(personagemService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonagemDTO>> listarPersonagens() {
        return ResponseEntity.ok(personagemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonagemDTO> buscarPersonagemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(personagemService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonagemDTO> atualizarPersonagem(@PathVariable Long id, @Valid @RequestBody PersonagemDTO dto) {
        return ResponseEntity.ok(personagemService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPersonagem(@PathVariable Long id) {
        personagemService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
