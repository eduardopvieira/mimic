package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.OrigemDTO; // Importe seu DTO
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrigemService {

    private final OrigemRepository origemRepository;
    private final TalentoRepository talentoRepository;

    @Autowired
    public OrigemService(OrigemRepository origemRepository, TalentoRepository talentoRepository) {
        this.origemRepository = origemRepository;
        this.talentoRepository = talentoRepository;
    }

    @Transactional(readOnly = true)
    public List<OrigemDTO> listarTodas() {
        return origemRepository.findAll().stream()
                .map(OrigemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrigemDTO buscarPorId(Long id) {
        return origemRepository.findById(id)
                .map(OrigemDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada com o id: " + id));
    }

    @Transactional
    public OrigemDTO salvar(OrigemDTO dto) {
        Talento talentoInicial = talentoRepository.findById(dto.getTalentoInicialId())
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + dto.getTalentoInicialId()));

        Origem novaOrigem = new Origem();
        mapearDtoParaEntidade(dto, novaOrigem, talentoInicial);

        Origem origemSalva = origemRepository.save(novaOrigem);
        return new OrigemDTO(origemSalva);
    }

    @Transactional
    public OrigemDTO atualizar(Long id, OrigemDTO dto) {
        Origem origemExistente = origemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada com o id: " + id));

        Talento talentoInicial = talentoRepository.findById(dto.getTalentoInicialId())
                .orElseThrow(() -> new EntityNotFoundException("Talento não encontrado com id: " + dto.getTalentoInicialId()));

        mapearDtoParaEntidade(dto, origemExistente, talentoInicial);

        Origem origemAtualizada = origemRepository.save(origemExistente);
        return new OrigemDTO(origemAtualizada);
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!origemRepository.existsById(id)) {
            throw new EntityNotFoundException("Origem não encontrada com o id: " + id);
        }
        origemRepository.deleteById(id);
    }

    private void mapearDtoParaEntidade(OrigemDTO dto, Origem origem, Talento talento) {
        origem.setNome(dto.getNome());
        origem.setDescricao(dto.getDescricao());
        origem.setAtributosSugeridos(dto.getAtributosSugeridos());
        origem.setProficienciasPericia(dto.getProficienciasPericia());
        origem.setProficienciaFerramenta(dto.getProficienciaFerramenta());
        origem.setEquipamentoOpcaoA(dto.getEquipamentoOpcaoA());
        origem.setEquipamentoOpcaoB(dto.getEquipamentoOpcaoB());
        origem.setTalentoInicial(talento);
    }
}
