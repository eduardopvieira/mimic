package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrigemRepository extends JpaRepository<Origem, Long> {
    //metodos personalizados aqui (se precisar)
}
