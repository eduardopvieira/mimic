package br.edu.ufersa.mimic.repository.habilidades;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MagiaRepository extends JpaRepository<Magia, Long> {
    List<Magia> findByCirculo(Integer circulo);
}
