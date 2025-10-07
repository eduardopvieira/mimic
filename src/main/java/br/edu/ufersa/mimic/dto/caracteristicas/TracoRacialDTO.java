package br.edu.ufersa.mimic.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracoRacialDTO {

    private Long id;

    @NotBlank(message = "O nome do traço é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição do traço é obrigatória.")
    private String descricao;

    private Long magiaConcedidaId;

    public TracoRacialDTO(TracoRacial traco) {
        this.id = traco.getId();
        this.nome = traco.getNome();
        this.descricao = traco.getDescricao();
        if (traco.getMagiaConcedida() != null) {
            this.magiaConcedidaId = traco.getMagiaConcedida().getId();
        }
    }
}
