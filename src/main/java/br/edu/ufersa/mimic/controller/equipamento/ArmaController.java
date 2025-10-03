package br.edu.ufersa.mimic.controller.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaDTO;
import br.edu.ufersa.mimic.service.equipamento.ArmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armas")
public class ArmaController {

    private final ArmaService armaService;

    @Autowired
    public ArmaController(ArmaService armaService) {
        this.armaService = armaService;
    }

    @PostMapping
    public ResponseEntity<ArmaDTO> criarArma(@Valid @RequestBody ArmaDTO dto) {
        return new ResponseEntity<>(armaService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArmaDTO>> listarArmas() {
        return ResponseEntity.ok(armaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArmaDTO> buscarArmaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(armaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArmaDTO> atualizarArma(@PathVariable Long id, @Valid @RequestBody ArmaDTO dto) {
        return ResponseEntity.ok(armaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArma(@PathVariable Long id) {
        armaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
