package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicasDeClasseRepository extends JpaRepository<CaracteristicaDeClasse, Long> {

    List<CaracteristicaDeClasse> findByClasse(Classe classe);

    List<CaracteristicaDeClasse> findBySubclasse(Subclasse subclasse);
}
