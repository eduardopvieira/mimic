package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.api.dto.caracteristicas.OrigemDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrigemService {

    @Autowired private OrigemRepository origemRepository;

    // --- LEITURA ---

    @Transactional(readOnly = true)
    public List<OrigemDTO> listarTodas(Long usuarioId) {
        return origemRepository.findAllPublicAndUser(usuarioId).stream()
                .map(OrigemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrigemDTO buscarPorId(Long id, Long usuarioId) {
        Origem origem = origemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada: " + id));

        // Validação de Segurança
        if (origem.getUsuario() != null && !origem.getUsuario().getUsuarioId().equals(usuarioId)) {
            throw new IllegalArgumentException("Acesso negado.");
        }

        return new OrigemDTO(origem);
    }

    // --- ESCRITA (USANDO A LÓGICA DO MODEL) ---

    @Transactional
    public OrigemDTO salvar(OrigemDTO dto, Long usuarioId) {
        // 1. Define o dono no DTO antes de criar a entidade
        dto.setUsuarioId(usuarioId);

        // 2. O construtor da Entidade faz todo o trabalho sujo (mapeia IDs, Enums, etc)
        Origem origem = new Origem(dto);

        // 3. Salva e retorna convertido
        return new OrigemDTO(origemRepository.save(origem));
    }

    @Transactional
    public OrigemDTO atualizar(Long id, OrigemDTO dto, Long usuarioId) {
        // 1. Busca a origem garantindo que pertence ao usuário
        Origem origemExistente = origemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada ou permissão negada."));

        // 2. Garante que o DTO tenha o ID do usuário correto
        dto.setUsuarioId(usuarioId);

        // 3. O método da entidade atualiza os campos
        origemExistente.atualizarDados(dto);

        return new OrigemDTO(origemRepository.save(origemExistente));
    }

    @Transactional
    public void deletarPorId(Long id, Long usuarioId) {
        Origem origem = origemRepository.findByIdAndUsuario_UsuarioId(id, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao deletar: Origem não encontrada ou protegida."));

        origemRepository.delete(origem);
    }
}