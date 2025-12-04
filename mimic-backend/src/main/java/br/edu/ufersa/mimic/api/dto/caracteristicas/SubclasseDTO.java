package br.edu.ufersa.mimic.api.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubclasseDTO {

    private Long id;

    @NotBlank(message = "O nome da subclasse é obrigatório.")
    private String nome;

    @NotNull(message = "O ID da classe pai é obrigatório.")
    private Long classePaiId;

    public SubclasseDTO(Subclasse subclasse) {
        this.id = subclasse.getId();
        this.nome = subclasse.getNome();

        if (subclasse.getClassePai() != null) {
            this.classePaiId = subclasse.getClassePai().getId();
        }
    }
}