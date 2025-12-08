package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Subraca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubracaRepository extends JpaRepository<Subraca, Long> {
    List<Subraca> findByRacaId(Long classePaiId);
}
