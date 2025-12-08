package br.edu.ufersa.mimic.repository.habilidades;

import br.edu.ufersa.mimic.model.habilidades.Talento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TalentoRepository extends JpaRepository<Talento, Long> {
    Optional<Talento> findByNome(String nome);
}