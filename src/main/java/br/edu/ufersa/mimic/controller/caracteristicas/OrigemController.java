package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.service.caracteristicas.OrigemService;
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
    public ResponseEntity<List<Origem>> listarTodasOrigens() {
        return ResponseEntity.ok(origemService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Origem> buscarOrigemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(origemService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Origem> criarOrigem(@RequestBody Origem origem) {
        Origem novaOrigem = origemService.salvar(origem);
        return new ResponseEntity<>(novaOrigem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Origem> atualizarOrigem(@PathVariable Long id, @RequestBody Origem origem) {
        return ResponseEntity.ok(origemService.atualizar(id, origem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrigem(@PathVariable Long id) {
        origemService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
