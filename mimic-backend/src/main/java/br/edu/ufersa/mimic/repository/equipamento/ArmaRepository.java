package br.edu.ufersa.mimic.repository.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Arma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArmaRepository extends JpaRepository<Arma, Long> {
    Optional<Arma> findByNome(String nome);
}
