package br.edu.ufersa.mimic.repository.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrigemRepository extends JpaRepository<Origem, Long> {

    @Query("SELECT o FROM Origem o WHERE o.usuario IS NULL OR o.usuario.usuarioId = :uid")
    List<Origem> findAllPublicAndUser(@Param("uid") Long usuarioId);

    Optional<Origem> findByIdAndUsuario_UsuarioId(Long id, Long usuarioId);
}