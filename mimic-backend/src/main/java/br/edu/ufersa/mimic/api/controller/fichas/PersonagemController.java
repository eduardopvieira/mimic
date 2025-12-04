package br.edu.ufersa.mimic.api.controller.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.PersonagemDTO;
import br.edu.ufersa.mimic.service.fichas.PersonagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
@CrossOrigin(origins = "*")
public class PersonagemController {

    private final PersonagemService personagemService;

    @Autowired
    public PersonagemController(PersonagemService personagemService) {
        this.personagemService = personagemService;
    }


    @PostMapping
    public ResponseEntity<PersonagemDTO> criarPersonagem(
            @Valid @RequestBody PersonagemDTO dto,
            @RequestParam Long usuarioId) {

        return new ResponseEntity<>(personagemService.salvar(dto, usuarioId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonagemDTO>> listarPersonagens(
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(personagemService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonagemDTO> buscarPersonagemPorId(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(personagemService.buscarPorId(id, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonagemDTO> atualizarPersonagem(
            @PathVariable Long id,
            @Valid @RequestBody PersonagemDTO dto,
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(personagemService.atualizar(id, dto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPersonagem(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {

        personagemService.deletarPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}