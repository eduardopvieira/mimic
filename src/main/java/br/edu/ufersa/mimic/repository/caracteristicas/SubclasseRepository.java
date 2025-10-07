package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubclasseRepository extends JpaRepository<Subclasse, Long> {
    Optional<Subclasse> findByNome(String nome);
    List<Subclasse> findByClassePai(Classe classePai);
}
