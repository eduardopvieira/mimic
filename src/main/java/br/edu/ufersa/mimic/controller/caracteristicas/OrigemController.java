package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.OrigemDTO;
// A importação da entidade 'Origem' não é mais necessária aqui
import br.edu.ufersa.mimic.service.caracteristicas.OrigemService;
import jakarta.validation.Valid; // Importe a anotação de validação
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/origens")
public class OrigemController {

    private final OrigemService origemService;

    @Autowired
    public OrigemController(OrigemService origemService) {
        this.origemService = origemService;
    }

    @GetMapping
    public ResponseEntity<List<OrigemDTO>> listarTodasOrigens() {
        return ResponseEntity.ok(origemService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrigemDTO> buscarOrigemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(origemService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrigemDTO> criarOrigem(@Valid @RequestBody OrigemDTO dto) {
        OrigemDTO novaOrigem = origemService.salvar(dto);
        return new ResponseEntity<>(novaOrigem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrigemDTO> atualizarOrigem(@PathVariable Long id, @Valid @RequestBody OrigemDTO dto) {
        return ResponseEntity.ok(origemService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrigem(@PathVariable Long id) {
        origemService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
