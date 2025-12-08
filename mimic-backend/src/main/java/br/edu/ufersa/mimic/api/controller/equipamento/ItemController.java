package br.edu.ufersa.mimic.api.controller.equipamento;

import br.edu.ufersa.mimic.api.dto.equipamento.ItemDTO;
import br.edu.ufersa.mimic.model.enums.TipoItem;
import br.edu.ufersa.mimic.service.equipamento.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarTodos(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(itemService.listarTudo(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ItemDTO>> listarPorTipo(
            @PathVariable TipoItem tipo,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(itemService.listarPorTipo(tipo, usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> buscarPorId(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(itemService.buscarPorId(id, usuarioId));
    }


    @PostMapping
    public ResponseEntity<ItemDTO> criarItem(
            @RequestBody @Valid ItemDTO dto,
            @RequestParam Long usuarioId) {

        ItemDTO novoItem = itemService.criarItemCustomizado(dto, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizarItem(
            @PathVariable Long id,
            @RequestBody ItemDTO dto,
            @RequestParam Long usuarioId) {

        return ResponseEntity.ok(itemService.atualizarItemCustomizado(id, dto, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(
            @PathVariable Long id,
            @RequestParam Long usuarioId) {

        itemService.deletarItemCustomizado(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}