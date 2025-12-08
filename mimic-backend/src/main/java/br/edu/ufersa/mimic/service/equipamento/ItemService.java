package br.edu.ufersa.mimic.service.equipamento;

import br.edu.ufersa.mimic.api.dto.equipamento.ItemDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.enums.TipoItem;
import br.edu.ufersa.mimic.repository.equipamento.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired private ItemRepository itemRepository;


    @Transactional(readOnly = true)
    public List<ItemDTO> listarTudo(Long usuarioId) {
        return itemRepository.findAllPublicAndUser(usuarioId).stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> listarPorTipo(TipoItem tipo, Long usuarioId) {
        return itemRepository.findByTipoAndUser(tipo, usuarioId).stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemDTO buscarPorId(Long id, Long usuarioId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        if (item.getUsuario() != null && !item.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException("Acesso negado a este item customizado.");
        }

        return new ItemDTO(item);
    }


    @Transactional
    public ItemDTO criarItemCustomizado(ItemDTO dto, Long usuarioId) {
        Item item = new Item();

        Usuario dono = new Usuario();
        dono.setUsuarioId(usuarioId);
        item.setUsuario(dono);

        mapearDtoParaEntidade(dto, item);
        return new ItemDTO(itemRepository.save(item));
    }

    @Transactional
    public ItemDTO atualizarItemCustomizado(Long id, ItemDTO dto, Long usuarioId) {
        Item item = itemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado ou você não tem permissão para editá-lo (Itens do sistema são protegidos)."));

        mapearDtoParaEntidade(dto, item);
        return new ItemDTO(itemRepository.save(item));
    }

    @Transactional
    public void deletarItemCustomizado(Long id, Long usuarioId) {
        Item item = itemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado ou você não tem permissão para deletá-lo."));

        itemRepository.delete(item);
    }

    private void mapearDtoParaEntidade(ItemDTO dto, Item item) {
        item.setNome(dto.getNome());
        item.setTipo(dto.getTipo());
        item.setDescricao(dto.getDescricao());
        item.setPeso(dto.getPeso());
        item.setCusto(dto.getCusto());

        item.setDano(dto.getDano());
        item.setTipoDano(dto.getTipoDano());
        item.setPropriedades(dto.getPropriedades());
        item.setMaestria(dto.getMaestria());
        item.setDistancia(dto.getDistancia());

        item.setCaBase(dto.getCaBase());
        item.setAddDestreza(dto.getAddDestreza());
        item.setMaxDestreza(dto.getMaxDestreza());
        item.setRequisitoForca(dto.getRequisitoForca());
        item.setDesvantagemFurtividade(dto.getDesvantagemFurtividade());
    }
}