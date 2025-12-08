package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TracoRacialRepository extends JpaRepository<TracoRacial, Long> {
    List<TracoRacial> findByRacaId(Long racaPaiId);

}
