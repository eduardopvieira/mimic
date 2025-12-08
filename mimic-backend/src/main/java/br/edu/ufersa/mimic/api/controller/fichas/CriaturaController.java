package br.edu.ufersa.mimic.api.controller.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.service.fichas.CriaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/criaturas")
public class CriaturaController {

    @Autowired private CriaturaService service;

    @PostMapping
    public ResponseEntity<CriaturaDTO> criar(@RequestBody CriaturaDTO dto, @RequestParam Long usuarioId) {
        return new ResponseEntity<>(service.salvar(dto, usuarioId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriaturaDTO> atualizar(
            @PathVariable Long id,
            @RequestBody CriaturaDTO dto,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(service.atualizar(id, dto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        service.deletar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriaturaDTO> buscarPorId(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(service.buscarPorId(id, usuarioId));
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<Void> uploadImagem(
            @PathVariable Long id,
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("file") MultipartFile file) throws IOException {

        service.salvarImagem(id, usuarioId, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CriaturaDTO>> listar(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }
}