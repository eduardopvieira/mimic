package br.edu.ufersa.mimic.dto.auth;

import br.edu.ufersa.mimic.model.auth.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Campo e-mail é obrigatorio.")
    private String email;

    @NotBlank(message = "Senha é obrigatoria")
    private String senha;

    public UsuarioDTO(Usuario user) {
        this.email = user.getEmail();
        this.senha = user.getSenha();
    }
}
