package br.edu.ufersa.mimic.api.controller.geral;

import br.edu.ufersa.mimic.api.dto.caracteristicas.RacaDTO;
import br.edu.ufersa.mimic.api.dto.caracteristicas.SubracaDTO;
import br.edu.ufersa.mimic.api.dto.caracteristicas.TracoRacialDTO;
import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.Subraca;
import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import br.edu.ufersa.mimic.repository.caracteristicas.RacaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.SubracaRepository;
import br.edu.ufersa.mimic.repository.caracteristicas.TracoRacialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/racas")
public class RacaController {

    @Autowired
    private RacaRepository racaRepository;
    @Autowired
    private SubracaRepository subRacaRepository;

    @GetMapping
    public ResponseEntity<List<RacaDTO>> listarRacas() {
        List<Raca> racas = racaRepository.findAll();
        List<RacaDTO> dtos = racas.stream().map(RacaDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{racaId}/subracas")
    public ResponseEntity<List<SubracaDTO>> listarSubracasPorRaca(@PathVariable Long racaId) {

        List<Subraca> lista = subRacaRepository.findByRacaId(racaId);

        List<SubracaDTO> dtos = lista.stream()
                .map(SubracaDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}