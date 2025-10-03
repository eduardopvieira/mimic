package br.edu.ufersa.mimic.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RacaDTO {

    private Long id;
    @NotBlank(message = "O nome da raça é obrigatório.")
    private String nome;

    @NotNull(message = "O deslocamento da raça é obrigatório.")
    @Positive(message = "O deslocamento deve ser um valor positivo.")
    private Integer deslocamento;

    @NotNull(message = "O tamanho da raça é obrigatório.")
    private Tamanho tamanho;

    private List<Long> tracosIds;

    public RacaDTO(Raca raca) {
        this.id = raca.getId();
        this.nome = raca.getNome();
        this.deslocamento = raca.getDeslocamento();
        this.tamanho = raca.getTamanho();
        if (raca.getTracoRacials() != null) {
            this.tracosIds = raca.getTracoRacials().stream().map(traco -> traco.getId()).toList();
        }
    }

}
