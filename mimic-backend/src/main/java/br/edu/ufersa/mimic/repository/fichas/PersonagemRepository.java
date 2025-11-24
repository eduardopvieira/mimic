package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    Optional<Personagem> findByNomePersonagem(String nomePersonagem);

    // Query Especial para "Carregar Tudo" de uma vez só.
    // Evita erros de Lazy Loading na hora de gerar o PDF.
    @Query("SELECT p FROM Personagem p " +
            "LEFT JOIN FETCH p.classe " +
            "LEFT JOIN FETCH p.subclasse " +
            "LEFT JOIN FETCH p.raca " +
            "LEFT JOIN FETCH p.origem " +
            "LEFT JOIN FETCH p.inventario " + // Traz os itens
            "WHERE p.id = :id")
    Optional<Personagem> findByIdWithAssociations(@Param("id") Long id);

    // Spring Data é esperto: ele entende o caminho pelo nome do método.
    // "FindBy" + "Usuario" (objeto) + "UsuarioId" (campo dentro do objeto)
    List<Personagem> findByUsuario_UsuarioId(Long usuarioId);

    // Se quiser garantir que ninguém acesse a ficha de outro pelo ID direto:
    Optional<Personagem> findByIdAndUsuario_UsuarioId(Long id, Long usuarioId);
}