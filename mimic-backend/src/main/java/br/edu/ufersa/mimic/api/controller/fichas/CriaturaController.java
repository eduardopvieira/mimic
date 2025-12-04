package br.edu.ufersa.mimic.api.controller.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.service.fichas.CriaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criaturas")
@CrossOrigin(origins = "*")
public class CriaturaController {

    private final CriaturaService criaturaService;

    @Autowired
    public CriaturaController(CriaturaService criaturaService) {
        this.criaturaService = criaturaService;
    }


    @GetMapping
    public ResponseEntity<List<CriaturaDTO>> listarCriaturas(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(criaturaService.listarTodas(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriaturaDTO> buscarCriaturaPorId(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(criaturaService.buscarPorId(id, usuarioId));
    }


    @PostMapping
    public ResponseEntity<CriaturaDTO> criarCriatura(
            @Valid @RequestBody CriaturaDTO dto,
            @RequestParam Long usuarioId) {

        return new ResponseEntity<>(criaturaService.salvar(dto, usuarioId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriaturaDTO> atualizarCriatura(
            @PathVariable Long id,
            @Valid @RequestBody CriaturaDTO dto,
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(criaturaService.atualizar(id, dto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCriatura(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {

        criaturaService.deletarPorId(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}