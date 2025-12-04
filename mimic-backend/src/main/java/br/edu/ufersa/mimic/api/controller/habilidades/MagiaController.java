package br.edu.ufersa.mimic.api.controller.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.MagiaDTO;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.service.habilidades.MagiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/magias")
public class MagiaController {

    private final MagiaService magiaService;

    @Autowired
    public MagiaController(MagiaService magiaService) {
        this.magiaService = magiaService;
    }

    // LISTAR TUDO (GET /api/magias)
    @GetMapping
    public ResponseEntity<List<MagiaDTO>> listarTodas() {
        List<Magia> magias = magiaService.listarTodas();
        // Converte a lista de Entidades para lista de DTOs
        List<MagiaDTO> dtos = magias.stream()
                                    .map(MagiaDTO::new)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // BUSCAR POR ID (GET /api/magias/{id})
    @GetMapping("/{id}")
    public ResponseEntity<MagiaDTO> buscarPorId(@PathVariable Long id) {
        Magia magia = magiaService.buscarPorId(id);
        return ResponseEntity.ok(new MagiaDTO(magia));
    }

    // BUSCAR POR NOME (GET /api/magias/busca?nome=Bola de Fogo)
    @GetMapping("/busca")
    public ResponseEntity<List<MagiaDTO>> buscarPorNome(@RequestParam String nome) {
        // ATENÇÃO: Você precisa garantir que o Service tenha esse método 'buscarPorNome'
        List<Magia> magias = magiaService.buscarPorNome(nome);
        
        List<MagiaDTO> dtos = magias.stream()
                                    .map(MagiaDTO::new)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // CRIAR (POST /api/magias)
    @PostMapping
    public ResponseEntity<MagiaDTO> criar(@RequestBody MagiaDTO dto) {
        Magia magiaParaSalvar = new Magia(dto);
        Magia magiaSalva = magiaService.salvar(magiaParaSalvar);
        return new ResponseEntity<>(new MagiaDTO(magiaSalva), HttpStatus.CREATED);
    }

    // ATUALIZAR (PUT /api/magias/{id})
    @PutMapping("/{id}")
    public ResponseEntity<MagiaDTO> atualizar(@PathVariable Long id, @RequestBody MagiaDTO dto) {
        Magia magiaParaAtualizar = new Magia(dto);
        
        Magia magiaAtualizada = magiaService.atualizar(id, magiaParaAtualizar);
        
        return ResponseEntity.ok(new MagiaDTO(magiaAtualizada));
    }

    // DELETAR (DELETE /api/magias/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        magiaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }


}