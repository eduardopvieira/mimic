package br.edu.ufersa.mimic.controller.fichas;

import br.edu.ufersa.mimic.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.service.fichas.CriaturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criaturas")
public class CriaturaController {

    private final CriaturaService criaturaService;

    @Autowired
    public CriaturaController(CriaturaService criaturaService) {
        this.criaturaService = criaturaService;
    }

    @PostMapping
    public ResponseEntity<CriaturaDTO> criarCriatura(@Valid @RequestBody CriaturaDTO dto) {
        return new ResponseEntity<>(criaturaService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CriaturaDTO>> listarCriaturas() {
        return ResponseEntity.ok(criaturaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriaturaDTO> buscarCriaturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(criaturaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriaturaDTO> atualizarCriatura(@PathVariable Long id, @Valid @RequestBody CriaturaDTO dto) {
        return ResponseEntity.ok(criaturaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCriatura(@PathVariable Long id) {
        criaturaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
