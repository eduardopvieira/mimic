package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicasDeClasseRepository extends JpaRepository<CaracteristicaDeClasse, Long> {

    //metodos de busca personalizados aqui (se precisar)
}
