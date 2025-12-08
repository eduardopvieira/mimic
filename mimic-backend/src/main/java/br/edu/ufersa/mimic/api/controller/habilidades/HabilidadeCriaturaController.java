package br.edu.ufersa.mimic.api.controller.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.HabilidadeCriaturaDTO;
import br.edu.ufersa.mimic.service.habilidades.HabilidadeCriaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/habilidades_criatura")
@CrossOrigin(origins = "*")
public class HabilidadeCriaturaController {
    @Autowired
    private HabilidadeCriaturaService hcService;

    @GetMapping
    public ResponseEntity<List<HabilidadeCriaturaDTO>> listarHabilidadesCriaturas() {
        return ResponseEntity.ok(hcService.listarTodasHabilidadesCriaturas());
    }
}
