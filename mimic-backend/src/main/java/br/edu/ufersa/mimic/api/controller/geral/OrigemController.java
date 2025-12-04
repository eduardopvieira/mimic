package br.edu.ufersa.mimic.api.controller.geral;

import br.edu.ufersa.mimic.api.dto.caracteristicas.OrigemDTO;
import jakarta.validation.Valid;
import br.edu.ufersa.mimic.service.caracteristicas.OrigemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/origens")
@CrossOrigin(origins = "*")
public class OrigemController {

    @Autowired
    private OrigemService origemService;

    @GetMapping
    public ResponseEntity<List<OrigemDTO>> listar(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(origemService.listarTodas(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrigemDTO> buscarPorId(@PathVariable Long id, @RequestParam Long usuarioId) {
        return ResponseEntity.ok(origemService.buscarPorId(id, usuarioId));
    }

    @PostMapping
    public ResponseEntity<OrigemDTO> criar(@RequestBody @Valid OrigemDTO dto, @RequestParam Long usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(origemService.salvar(dto, usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrigemDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid OrigemDTO dto,
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(origemService.atualizar(id, dto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @RequestParam Long usuarioId) {
        origemService.deletarPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}