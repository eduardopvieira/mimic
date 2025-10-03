package br.edu.ufersa.mimic.controller.habilidades;

import br.edu.ufersa.mimic.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import br.edu.ufersa.mimic.service.habilidades.TalentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talentos")
public class TalentoController {

    private final TalentoService talentoService;

    @Autowired
    public TalentoController(TalentoService talentoService) {
        this.talentoService = talentoService;
    }

    @GetMapping
    public ResponseEntity<List<TalentoDTO>> listarTalentos(
            @RequestParam(required = false) CategoriaTalento categoria) {
        return ResponseEntity.ok(talentoService.listarTodos(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalentoDTO> buscarTalentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(talentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TalentoDTO> criarTalento(@Valid @RequestBody TalentoDTO dto) {
        TalentoDTO novoTalento = talentoService.salvar(dto);
        return new ResponseEntity<>(novoTalento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalentoDTO> atualizarTalento(@PathVariable Long id, @Valid @RequestBody TalentoDTO dto) {
        return ResponseEntity.ok(talentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTalento(@PathVariable Long id) {
        talentoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
