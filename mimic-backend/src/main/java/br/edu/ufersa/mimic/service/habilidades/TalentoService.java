package br.edu.ufersa.mimic.service.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.repository.habilidades.TalentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TalentoService {

    @Autowired private TalentoRepository talentoRepository;

    @Transactional(readOnly = true)
    public List<TalentoDTO> listarTodosTalentos() {
        return talentoRepository.findAll().stream()
                .map(TalentoDTO::new)
                .collect(Collectors.toList());
    }
}