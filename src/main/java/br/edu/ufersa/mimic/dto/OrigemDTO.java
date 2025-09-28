package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrigemDTO {

    private Long id;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    private String descricao;

    @NotEmpty(message = "A origem deve sugerir pelo menos um atributo.")
    private Set<String> atributosSugeridos;

    @NotEmpty(message = "A origem deve conceder pelo menos uma proficiência em perícia.")
    private Set<String> proficienciasPericia;

    @NotBlank(message = "A proficiência em ferramenta não pode ser vazia.")
    private String proficienciaFerramenta;

    private String equipamentoOpcaoA;

    @NotNull(message = "A opção B de equipamento (valor em PO) é obrigatória.")
    private Integer equipamentoOpcaoB;

    @NotNull(message = "O ID do talento inicial é obrigatório.")
    @Positive
    private Long talentoInicialId;

    public OrigemDTO(Origem origem) {
        this.id = origem.getId();
        this.nome = origem.getNome();
        this.descricao = origem.getDescricao();
        this.atributosSugeridos = origem.getAtributosSugeridos();
        this.proficienciasPericia = origem.getProficienciasPericia();
        this.proficienciaFerramenta = origem.getProficienciaFerramenta();
        this.equipamentoOpcaoA = origem.getEquipamentoOpcaoA();
        this.equipamentoOpcaoB = origem.getEquipamentoOpcaoB();
        if (origem.getTalentoInicial() != null) {
            this.talentoInicialId = origem.getTalentoInicial().getId();
        }
    }
}

