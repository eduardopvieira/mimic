package br.edu.ufersa.mimic.repository.habilidades;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MagiaRepository extends JpaRepository<Magia, Long> {
    List<Magia> findByCirculo(Integer circulo);

    @Query("SELECT m FROM Magia m WHERE m.usuario IS NULL OR m.usuario.usuarioId = :uid")
    List<Magia> findAllPublicAndUser(@Param("uid") Long usuarioId);

    // Exemplo de filtro por círculo + segurança
    @Query("SELECT m FROM Magia m WHERE m.circulo = :circulo AND (m.usuario IS NULL OR m.usuario.usuarioId = :uid)")
    List<Magia> findByCirculoAndUser(@Param("circulo") Integer circulo, @Param("uid") Long usuarioId);
}
