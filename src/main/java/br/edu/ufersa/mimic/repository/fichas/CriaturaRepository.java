package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Criatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CriaturaRepository extends JpaRepository<Criatura, Long> {
    Optional<Criatura> findByNome(String nome);
}
