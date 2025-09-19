package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    //metodos personalizados aqui (se precisar)
}
