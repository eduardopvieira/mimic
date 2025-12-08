package br.edu.ufersa.mimic.repository.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.enums.TipoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.usuario IS NULL OR i.usuario.usuarioId = :uid")
    List<Item> findAllPublicAndUser(@Param("uid") Long usuarioId);

    @Query("SELECT i FROM Item i WHERE i.tipo = :tipo AND (i.usuario IS NULL OR i.usuario.usuarioId = :uid)")
    List<Item> findByTipoAndUser(@Param("tipo") TipoItem tipo, @Param("uid") Long usuarioId);

    Optional<Item> findByIdAndUsuario_UsuarioId(Long id, Long usuarioId);

    Optional<Item> findById(Long id);
}