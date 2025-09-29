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

    private Long id;

    @NotNull
    @Positive
    private Integer nivelAdquirido;

    private Long classeId;

    private Long subclasseId;

    @NotNull
    private Long tracoId;

    private String nomeTraco;
    private String nomeClasse;
    private String nomeSubclasse;


    public CaracteristicaDeClasseDTO(CaracteristicaDeClasse entity) {
        this.id = entity.getId();
        this.nivelAdquirido = entity.getNivelAdquirido();

        if (entity.getClasse() != null) {
            this.classeId = entity.getClasse().getId();
            this.nomeClasse = entity.getClasse().getNome().toString();
        }

        if (entity.getSubclasse() != null) {
            this.subclasseId = entity.getSubclasse().getId();
            this.nomeSubclasse = entity.getSubclasse().getNome();
        }

        if (entity.getTraco() != null) {
            this.tracoId = entity.getTraco().getId();
            this.nomeTraco = entity.getTraco().getNome();
        }
    }
}
