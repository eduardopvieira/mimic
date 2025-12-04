package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Criatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriaturaRepository extends JpaRepository<Criatura, Long> {

    @Query("SELECT c FROM Criatura c WHERE c.usuario IS NULL OR c.usuario.usuarioId = :uid")
    List<Criatura> findAllPublicAndUser(@Param("uid") Long usuarioId);

    Optional<Criatura> findByIdAndUsuario_UsuarioId(Long id, Long usuarioId);

    Optional<Criatura> findByNome(String nome);
}