package br.edu.ufersa.mimic.repository.fichas;

import br.edu.ufersa.mimic.model.fichas.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    Optional<Personagem> findByNomePersonagem(String nomePersonagem);
}
