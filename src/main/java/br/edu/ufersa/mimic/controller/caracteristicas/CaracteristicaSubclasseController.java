package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.dto.CaracteristicaSubclasseDTO;
import br.edu.ufersa.mimic.service.caracteristicas.CaracteristicaSubclasseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas-subclasse")
public class CaracteristicaSubclasseController {

    private final CaracteristicaSubclasseService caracteristicaService;

    @Autowired
    public CaracteristicaSubclasseController(CaracteristicaSubclasseService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @PostMapping
    public ResponseEntity<CaracteristicaSubclasseDTO> criar(@Valid @RequestBody CaracteristicaSubclasseDTO dto) {
        return new ResponseEntity<>(caracteristicaService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaSubclasseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(caracteristicaService.buscarPorId(id));
    }

    @GetMapping("/subclasse/{subclasseId}")
    public ResponseEntity<List<CaracteristicaSubclasseDTO>> listarPorSubclasse(@PathVariable Long subclasseId) {
        return ResponseEntity.ok(caracteristicaService.listarPorSubclasse(subclasseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaSubclasseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CaracteristicaSubclasseDTO dto) {
        return ResponseEntity.ok(caracteristicaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        caracteristicaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
