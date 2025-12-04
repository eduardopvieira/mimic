package br.edu.ufersa.mimic.api.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "O nome da característica é obrigatório")
    private String nome;

    private String descricao;

    @NotNull
    @Positive
    private Integer nivelAdquirido;

    private Long classeId;
    private String nomeClasse;

    private Long subclasseId;
    private String nomeSubclasse;

    public CaracteristicaDeClasseDTO(CaracteristicaDeClasse entity) {
        this.id = entity.getId();
        this.nivelAdquirido = entity.getNivelAdquirido();

        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();

        if (entity.getClasse() != null) {
            this.classeId = entity.getClasse().getId();
            this.nomeClasse = entity.getClasse().getNome();
        }

        if (entity.getSubclasse() != null) {
            this.subclasseId = entity.getSubclasse().getId();
            this.nomeSubclasse = entity.getSubclasse().getNome();
        }
    }
}