package br.edu.ufersa.mimic.repository.habilidades;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MagiaRepository extends JpaRepository<Magia, Long> {
    
    List<Magia> findByNomeContainingIgnoreCase(String nome);

}