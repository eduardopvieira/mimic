package br.edu.ufersa.mimic.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaSubclasse;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class SubclasseDTO {

    private Long id;

    @NotBlank(message = "O nome da subclasse é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "O ID da classe pai é obrigatório.")
    private Long classePaiId;

    private List<Long> caracteristicasIds;

    public SubclasseDTO(Subclasse subclasse) {
        this.id = subclasse.getId();
        this.nome = subclasse.getNome();
        this.descricao = subclasse.getDescricao();

        if (subclasse.getClassePai() != null) {
            this.classePaiId = subclasse.getClassePai().getId();
        }

        if (subclasse.getCaracteristicas() != null) {
            this.caracteristicasIds = subclasse.getCaracteristicas().stream()
                    .map(CaracteristicaSubclasse::getId)
                    .collect(Collectors.toList());
        }
    }
}
