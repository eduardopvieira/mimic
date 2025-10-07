package br.edu.ufersa.mimic.repository.auth;

import br.edu.ufersa.mimic.model.auth.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
