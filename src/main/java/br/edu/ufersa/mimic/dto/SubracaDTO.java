package br.edu.ufersa.mimic.dto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubracaDTO {

    private Long id;

    @NotBlank(message = "O nome da sub-raça é obrigatório.")
    private String nome;

    private Long racaPrincipalId;
}
