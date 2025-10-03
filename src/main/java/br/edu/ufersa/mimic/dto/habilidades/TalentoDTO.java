package br.edu.ufersa.mimic.dto.habilidades;

import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TalentoDTO {

    private Long id;

    @NotBlank(message = "O nome do talento é obrigatório.")
    private String nome;

    @NotNull(message = "A categoria do talento é obrigatória.")
    private CategoriaTalento categoria;

    private String preRequisito; // pode ser nulo (nao tem pre-requisito)

    @NotBlank(message = "A descrição do talento é obrigatória.")
    private String descricao;

    @NotNull(message = "É obrigatório informar se o talento é repetível.")
    private Boolean isRepetivel;

    public TalentoDTO(Talento talento) {
        this.id = talento.getId();
        this.nome = talento.getNome();
        this.categoria = talento.getCategoria();
        this.preRequisito = talento.getPreRequisito();
        this.descricao = talento.getDescricao();
        this.isRepetivel = talento.isRepetivel();
    }

}
