package br.edu.ufersa.mimic.repository.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Armadura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArmaduraRepository extends JpaRepository<Armadura, Long> {
    Optional<Armadura> findByNome(String nome);
}
