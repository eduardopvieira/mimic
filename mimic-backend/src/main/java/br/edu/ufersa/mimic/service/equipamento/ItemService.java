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

    // --- LEITURA ---

    @Transactional(readOnly = true)
    public List<ItemDTO> listarTudo(Long usuarioId) {
        // Retorna: Itens Oficiais + Itens Homebrew deste usuário
        return itemRepository.findAllPublicAndUser(usuarioId).stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> listarPorTipo(TipoItem tipo, Long usuarioId) {
        // Útil para preencher abas separadas no front (Aba Armas, Aba Armaduras...)
        return itemRepository.findByTipoAndUser(tipo, usuarioId).stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemDTO buscarPorId(Long id, Long usuarioId) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        // Validação de Visualização:
        // Se o item tem dono E o dono não é o usuário atual -> BLOQUEIA
        if (item.getUsuario() != null && !item.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException("Acesso negado a este item customizado.");
        }

        return new ItemDTO(item);
    }

    // --- ESCRITA (Criação de Homebrew) ---

    @Transactional
    public ItemDTO criarItemCustomizado(ItemDTO dto, Long usuarioId) {
        Item item = new Item();

        // Vínculo de Segurança: O item pertence a quem criou
        Usuario dono = new Usuario();
        dono.setUsuarioId(usuarioId);
        item.setUsuario(dono);

        mapearDtoParaEntidade(dto, item);
        return new ItemDTO(itemRepository.save(item));
    }

    @Transactional
    public ItemDTO atualizarItemCustomizado(Long id, ItemDTO dto, Long usuarioId) {
        // Tenta buscar garantindo que o item pertence ao usuário.
        // Se o item for do Sistema (usuario = null), o findByIdAndUsuario... não vai achar,
        // impedindo a edição de regras oficiais.
        Item item = itemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado ou você não tem permissão para editá-lo (Itens do sistema são protegidos)."));

        mapearDtoParaEntidade(dto, item);
        return new ItemDTO(itemRepository.save(item));
    }

    @Transactional
    public void deletarItemCustomizado(Long id, Long usuarioId) {
        // Mesma lógica: só deleta se for seu.
        Item item = itemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado ou você não tem permissão para deletá-lo."));

        itemRepository.delete(item);
    }

    // --- AUXILIAR DE MAPEAMENTO ---
    // Como unificamos tudo em uma classe Item, mapeamos todos os campos possíveis.
    private void mapearDtoParaEntidade(ItemDTO dto, Item item) {
        // Campos Gerais
        item.setNome(dto.getNome());
        item.setTipo(dto.getTipo()); // ARMA, ARMADURA, ITEM...
        item.setDescricao(dto.getDescricao());
        item.setPeso(dto.getPeso());
        item.setCusto(dto.getCusto());

        // Campos de Arma (Só preencher se fizer sentido, ou deixar null)
        item.setDano(dto.getDano());
        item.setTipoDano(dto.getTipoDano());
        item.setPropriedades(dto.getPropriedades());
        item.setMaestria(dto.getMaestria());
        item.setDistancia(dto.getDistancia());

        // Campos de Armadura/Escudo
        item.setCaBase(dto.getCaBase());
        item.setAddDestreza(dto.getAddDestreza());
        item.setMaxDestreza(dto.getMaxDestreza());
        item.setRequisitoForca(dto.getRequisitoForca());
        item.setDesvantagemFurtividade(dto.getDesvantagemFurtividade());
    }
}