package br.edu.ufersa.mimic.service.habilidades;

import br.edu.ufersa.mimic.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TalentoService {

    private final TalentoRepository talentoRepository;

    @Autowired
    public TalentoService(TalentoRepository talentoRepository) {
        this.talentoRepository = talentoRepository;
    }

    @Transactional(readOnly = true)
    public List<TalentoDTO> listarTodos(CategoriaTalento categoria) {
        List<Talento> talentos = (categoria != null)
                ? talentoRepository.findByCategoria(categoria)
                : talentoRepository.findAll();

        return talentos.stream()
                .map(TalentoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TalentoDTO buscarPorId(Long id) {
        return talentoRepository.findById(id)
                .map(TalentoDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + id));
    }

    @Transactional
    public TalentoDTO salvar(TalentoDTO dto) {
        talentoRepository.findByNome(dto.getNome()).ifPresent(t -> {
            throw new IllegalArgumentException("Um talento com o nome '" + dto.getNome() + "' já existe.");
        });

        Talento talento = new Talento(dto);
        Talento talentoSalvo = talentoRepository.save(talento);
        return new TalentoDTO(talentoSalvo);
    }

    @Transactional
    public TalentoDTO atualizar(Long id, TalentoDTO dto) {
        Talento talentoExistente = talentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + id));

        talentoExistente.setNome(dto.getNome());
        talentoExistente.setCategoria(dto.getCategoria());
        talentoExistente.setPreRequisito(dto.getPreRequisito());
        talentoExistente.setDescricao(dto.getDescricao());
        talentoExistente.setRepetivel(dto.getIsRepetivel());

        Talento talentoAtualizado = talentoRepository.save(talentoExistente);
        return new TalentoDTO(talentoAtualizado);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!talentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Talento não encontrado com id: " + id);
        }
        talentoRepository.deleteById(id);
    }
}
