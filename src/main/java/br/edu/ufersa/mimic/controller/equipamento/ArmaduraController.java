package br.edu.ufersa.mimic.controller.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaduraDTO;
import br.edu.ufersa.mimic.service.equipamento.ArmaduraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/armaduras")
public class ArmaduraController {

    private final ArmaduraService armaduraService;

    @Autowired
    public ArmaduraController(ArmaduraService armaduraService) {
        this.armaduraService = armaduraService;
    }

    @PostMapping
    public ResponseEntity<ArmaduraDTO> criarArmadura(@Valid @RequestBody ArmaduraDTO dto) {
        return new ResponseEntity<>(armaduraService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ArmaduraDTO>> listarArmaduras() {
        return ResponseEntity.ok(armaduraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArmaduraDTO> buscarArmaduraPorId(@PathVariable Long id) {
        return ResponseEntity.ok(armaduraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArmaduraDTO> atualizarArmadura(@PathVariable Long id, @Valid @RequestBody ArmaduraDTO dto) {
        return ResponseEntity.ok(armaduraService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArmadura(@PathVariable Long id) {
        armaduraService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
