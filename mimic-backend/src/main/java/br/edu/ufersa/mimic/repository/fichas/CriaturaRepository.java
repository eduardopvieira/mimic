package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Criatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CriaturaRepository extends JpaRepository<Criatura, Long> {
    List<Criatura> findByUsuario_UsuarioId(Long usuarioId);
}