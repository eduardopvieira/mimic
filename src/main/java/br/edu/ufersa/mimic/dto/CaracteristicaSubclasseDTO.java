package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaSubclasse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaSubclasseDTO {

    private Long id;

    @NotBlank(message = "O nome da característica é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição da característica é obrigatória.")
    private String descricao;

    @NotNull(message = "O nível adquirido é obrigatório.")
    @Positive(message = "O nível deve ser maior que zero.")
    private int nivelAdquirido;

    @NotNull(message = "O ID da subclasse é obrigatório.")
    private Long subclasseId;

    public CaracteristicaSubclasseDTO(CaracteristicaSubclasse caracteristica) {
        this.id = caracteristica.getId();
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
        this.nivelAdquirido = caracteristica.getNivelAdquirido();
        if (caracteristica.getSubclasse() != null) {
            this.subclasseId = caracteristica.getSubclasse().getId();
        }
    }
}
