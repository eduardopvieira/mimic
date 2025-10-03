package br.edu.ufersa.mimic.controller.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ItemDTO;
import br.edu.ufersa.mimic.service.equipamento.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDTO> criarItem(@Valid @RequestBody ItemDTO dto) {
        return new ResponseEntity<>(itemService.salvar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listarItens() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> buscarItemPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> atualizarItem(@PathVariable Long id, @Valid @RequestBody ItemDTO dto) {
        return ResponseEntity.ok(itemService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
