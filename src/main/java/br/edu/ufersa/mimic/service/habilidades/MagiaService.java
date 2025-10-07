package br.edu.ufersa.mimic.service.habilidades;


import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.repository.habilidades.MagiaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagiaService {

    private final br.edu.ufersa.mimic.repository.habilidades.MagiaRepository magiaRepository;

    @Autowired
    public MagiaService(MagiaRepository magiaRepository) {
        this.magiaRepository = magiaRepository;
    }

    public List<Magia> listarTodas() {
        return magiaRepository.findAll();
    }

    public Magia buscarPorId(Long id) {
        return magiaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Magia não encontrada com o id: " + id));
    }

    public List<Magia> buscarPorCirculo(Integer circulo) {
        return magiaRepository.findByCirculo(circulo);
    }

    public Magia salvar(Magia magia) {
        return magiaRepository.save(magia);
    }

    public Magia atualizar(Long id, Magia magiaAtualizada) {
        Magia magiaExistente = buscarPorId(id);
        magiaExistente.setNome(magiaAtualizada.getNome());
        magiaExistente.setDescricao(magiaAtualizada.getDescricao());
        magiaExistente.setCirculo(magiaAtualizada.getCirculo());
        magiaExistente.setEscolaDeMagia(magiaAtualizada.getEscolaDeMagia());
        magiaExistente.setTempoConjuracao(magiaAtualizada.getTempoConjuracao());
        magiaExistente.setAlcance(magiaAtualizada.getAlcance());
        magiaExistente.setComponentes(magiaAtualizada.getComponentes());
        magiaExistente.setDuracao(magiaAtualizada.getDuracao());

        return magiaRepository.save(magiaExistente);
    }

    public void deletarPorId(Long id) {
        if (!magiaRepository.existsById(id)) {
            throw new EntityNotFoundException("Magia não encontrada com o id: " + id);
        }
        magiaRepository.deleteById(id);
    }
}
