package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.Traco;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracoDTO {

    private Long id;

    @NotBlank(message = "O nome do traço é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição do traço é obrigatória.")
    private String descricao;

    public TracoDTO(Traco traco) {
        this.id = traco.getId();
        this.nome = traco.getNome();
        this.descricao = traco.getDescricao();
    }
}
