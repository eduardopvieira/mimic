package br.edu.ufersa.mimic.api.controller.geral;

import br.edu.ufersa.mimic.api.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.service.habilidades.TalentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/talentos")
@CrossOrigin(origins = "*")
public class TalentoController {
    @Autowired
    private TalentoService talentoService;

    @GetMapping
    public ResponseEntity<List<TalentoDTO>> listarTalentos() {
        return ResponseEntity.ok(talentoService.listarTodosTalentos());

    }
}
