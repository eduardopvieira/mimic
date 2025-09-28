package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaDeClasseDTO {

    @NotNull(message = "O nível adquirido é obrigatório.")
    @Positive(message = "O nível deve ser maior que zero.")
    private Integer nivelAdquirido;

    // apenas um dos dois deve ser preenchido
    private Long classeId;
    private Long subclasseId;

    @NotNull(message = "O ID do traço é obrigatório.")
    private Long tracoId;

    CaracteristicaDeClasseDTO(CaracteristicaDeClasse caracteristicaDeClasse) {
        this.nivelAdquirido = caracteristicaDeClasse.getNivelAdquirido();
        if (caracteristicaDeClasse.getClasse() != null) {
            this.classeId = caracteristicaDeClasse.getClasse().getId();
        }
        if (caracteristicaDeClasse.getSubclasse() != null) {
            this.subclasseId = caracteristicaDeClasse.getSubclasse().getId();
        }
        this.tracoId = caracteristicaDeClasse.getTraco().getId();
    }
}
