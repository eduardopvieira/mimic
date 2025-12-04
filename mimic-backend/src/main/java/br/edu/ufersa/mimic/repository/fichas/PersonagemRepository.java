package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    Optional<Personagem> findByNome(String nomePersonagem);

    @Query("SELECT p FROM Personagem p " +
            "LEFT JOIN FETCH p.classe " +
            "LEFT JOIN FETCH p.subclasse " +
            "LEFT JOIN FETCH p.raca " +
            "LEFT JOIN FETCH p.origem " +
            "LEFT JOIN FETCH p.inventario " +
            "WHERE p.id = :id")
    Optional<Personagem> findByIdWithAssociations(@Param("id") Long id);

    List<Personagem> findByUsuario_UsuarioId(Long usuarioId);

    Optional<Personagem> findByIdAndUsuario_UsuarioId(Long id, Long usuarioId);
}