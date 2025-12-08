package br.edu.ufersa.mimic.api.controller.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.AcaoCriaturaDTO;
import br.edu.ufersa.mimic.service.habilidades.AcaoCriaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/acoes_criatura")
@CrossOrigin(origins = "*")
public class AcaoCriaturaController {
    @Autowired
    private AcaoCriaturaService acService;

    @GetMapping
    public ResponseEntity<List<AcaoCriaturaDTO>> listarAcoesCriaturas() {
        return ResponseEntity.ok(acService.listarTodasAcoesCriaturas());
    }
}
