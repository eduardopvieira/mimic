package br.edu.ufersa.mimic.service.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.repository.caracteristicas.OrigemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrigemService {

    private final OrigemRepository origemRepository;

    @Autowired
    public OrigemService(OrigemRepository origemRepository) {
        this.origemRepository = origemRepository;
    }

    public List<Origem> listarTodas() {
        return origemRepository.findAll();
    }

    public Origem buscarPorId(Long id) {
        return origemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Origem não encontrada com o id: " + id));
    }

    public Origem salvar(Origem origem) {
        return origemRepository.save(origem);
    }

    public Origem atualizar(Long id, Origem origemAtualizada) {
        Origem origemExistente = buscarPorId(id);

        origemExistente.setNome(origemAtualizada.getNome());
        origemExistente.setDescricao(origemAtualizada.getDescricao());
        origemExistente.setAtributosSugeridos(origemAtualizada.getAtributosSugeridos());
        origemExistente.setTalentoInicial(origemAtualizada.getTalentoInicial());
        origemExistente.setProficienciasPericia(origemAtualizada.getProficienciasPericia());
        origemExistente.setProficienciaFerramenta(origemAtualizada.getProficienciaFerramenta());
        origemExistente.setEquipamentoOpcaoA(origemAtualizada.getEquipamentoOpcaoA());
        origemExistente.setEquipamentoOpcaoB(origemAtualizada.getEquipamentoOpcaoB());

        return origemRepository.save(origemExistente);
    }

    public void deletarPorId(Long id) {
        if (!origemRepository.existsById(id)) {
            throw new EntityNotFoundException("Origem não encontrada com o id: " + id);
        }
        origemRepository.deleteById(id);
    }
}
