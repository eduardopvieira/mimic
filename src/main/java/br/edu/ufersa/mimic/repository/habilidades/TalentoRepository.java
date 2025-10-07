package br.edu.ufersa.mimic.repository.habilidades;

import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TalentoRepository extends JpaRepository<Talento, Long> {
    Optional<Talento> findByNome(String nome);
    List<Talento> findByCategoria(CategoriaTalento categoria);
}
