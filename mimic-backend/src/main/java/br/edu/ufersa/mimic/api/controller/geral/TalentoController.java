package br.edu.ufersa.mimic.api.controller.geral;

import br.edu.ufersa.mimic.api.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.service.geral.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/talentos") // <--- Defina a rota base aqui para facilitar
@CrossOrigin(origins = "*")
public class TalentoController { // Ou TalentoController se preferir
    @Autowired
    private BibliotecaService bibliotecaService;
    // ou private TalentoRepository talentoRepository;

    // --- ENDPOINT DE LISTAGEM ---
    @GetMapping
    public ResponseEntity<List<TalentoDTO>> listarTalentos() {
        // Se usar o service:
        return ResponseEntity.ok(bibliotecaService.listarTodosTalentos());

        // OU se usar o repository direto (mais rápido para testar):
        // return ResponseEntity.ok(talentoRepository.findAll().stream().map(TalentoDTO::new).toList());
    }
}
