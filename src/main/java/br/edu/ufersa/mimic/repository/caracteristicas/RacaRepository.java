package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RacaRepository extends JpaRepository<Raca, Long> {
    Optional<Raca> findByNome(String nome);
}
