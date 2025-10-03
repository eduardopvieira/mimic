package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.RacaDTO;
import br.edu.ufersa.mimic.service.RacaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racas")
public class RacaController {

    private final RacaService racaService;

    @Autowired
    public RacaController(RacaService racaService) {
        this.racaService = racaService;
    }

    @PostMapping
    public ResponseEntity<RacaDTO> criarRaca(@Valid @RequestBody RacaDTO dto) {
        RacaDTO novaRaca = racaService.salvar(dto);
        return new ResponseEntity<>(novaRaca, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacaDTO> buscarRacaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(racaService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<RacaDTO>> listarTodasRacas() {
        return ResponseEntity.ok(racaService.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RacaDTO> atualizarRaca(@PathVariable Long id, @Valid @RequestBody RacaDTO dto) {
        return ResponseEntity.ok(racaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRaca(@PathVariable Long id) {
        racaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
