package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Criatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriaturaRepository extends JpaRepository<Criatura, Long> {
}
