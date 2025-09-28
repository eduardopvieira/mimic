package br.edu.ufersa.mimic.controller;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.service.MagiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/magias")
public class MagiaController {

    private final MagiaService magiaService;

    @Autowired
    public MagiaController(MagiaService magiaService) {
        this.magiaService = magiaService;
    }

    @GetMapping
    public ResponseEntity<List<Magia>> listarTodasMagias() {
        return ResponseEntity.ok(magiaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Magia> buscarMagiaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(magiaService.buscarPorId(id));
    }

    @GetMapping("/circulo/{circulo}")
    public ResponseEntity<List<Magia>> buscarMagiasPorCirculo(@PathVariable Integer circulo) {
        return ResponseEntity.ok(magiaService.buscarPorCirculo(circulo));
    }

    @PostMapping
    public ResponseEntity<Magia> criarMagia(@RequestBody Magia magia) {
        Magia novaMagia = magiaService.salvar(magia);
        return new ResponseEntity<>(novaMagia, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Magia> atualizarMagia(@PathVariable Long id, @RequestBody Magia magia) {
        return ResponseEntity.ok(magiaService.atualizar(id, magia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMagia(@PathVariable Long id) {
        magiaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}