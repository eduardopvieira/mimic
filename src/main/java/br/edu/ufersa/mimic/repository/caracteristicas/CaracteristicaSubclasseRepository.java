package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaSubclasse;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaracteristicaSubclasseRepository extends JpaRepository<CaracteristicaSubclasse, Long> {

    List<CaracteristicaSubclasse> findBySubclasse(Subclasse subclasse);
    Optional<CaracteristicaSubclasse> findBySubclasseNome(String subclasse);
}
