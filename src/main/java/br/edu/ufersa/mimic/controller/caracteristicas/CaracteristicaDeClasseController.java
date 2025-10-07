package br.edu.ufersa.mimic.controller.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.CaracteristicaDeClasseDTO;
import br.edu.ufersa.mimic.service.caracteristicas.CaracteristicaDeClasseService; // Supondo que o Service exista
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas-classe")
public class CaracteristicaDeClasseController {

    private final CaracteristicaDeClasseService caracteristicaService;

    @Autowired
    public CaracteristicaDeClasseController(CaracteristicaDeClasseService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @PostMapping
    public ResponseEntity<CaracteristicaDeClasseDTO> criarCaracteristica(@Valid @RequestBody CaracteristicaDeClasseDTO dto) {
        CaracteristicaDeClasseDTO novaCaracteristica = caracteristicaService.salvar(dto);
        return new ResponseEntity<>(novaCaracteristica, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaDeClasseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(caracteristicaService.buscarPorId(id));
    }

    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<CaracteristicaDeClasseDTO>> listarPorClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(caracteristicaService.listarPorClasse(classeId));
    }

    @GetMapping("/subclasse/{subclasseId}")
    public ResponseEntity<List<CaracteristicaDeClasseDTO>> listarPorSubclasse(@PathVariable Long subclasseId) {
        return ResponseEntity.ok(caracteristicaService.listarPorSubclasse(subclasseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaDeClasseDTO> atualizarCaracteristica(@PathVariable Long id, @Valid @RequestBody CaracteristicaDeClasseDTO dto) {
        return ResponseEntity.ok(caracteristicaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCaracteristica(@PathVariable Long id) {
        caracteristicaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
