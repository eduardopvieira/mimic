package br.edu.ufersa.mimic.service.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ItemDTO;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.repository.equipamento.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemDTO salvar(ItemDTO dto) {
        itemRepository.findByNome(dto.getNome()).ifPresent(i -> {
            throw new IllegalArgumentException("Um item com o nome '" + dto.getNome() + "' já existe.");
        });

        Item item = new Item(dto);
        Item itemSalvo = itemRepository.save(item);
        return new ItemDTO(itemSalvo);
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> listarTodos() {
        return itemRepository.findAll().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemDTO buscarPorId(Long id) {
        return itemRepository.findById(id)
                .map(ItemDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com id: " + id));
    }

    @Transactional
    public ItemDTO atualizar(Long id, ItemDTO dto) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item não encontrado com id: " + id);
        }
        dto.setId(id);
        Item itemParaAtualizar = new Item(dto);
        Item itemAtualizado = itemRepository.save(itemParaAtualizar);
        return new ItemDTO(itemAtualizado);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item não encontrado com id: " + id);
        }
        itemRepository.deleteById(id);
    }
}
