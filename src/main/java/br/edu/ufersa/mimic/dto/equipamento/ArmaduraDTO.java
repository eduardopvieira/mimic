package br.edu.ufersa.mimic.dto.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Armadura;
import br.edu.ufersa.mimic.model.enums.equipamento.armadura.CategoriaArmadura;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArmaduraDTO {

    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "A categoria é obrigatória.")
    private CategoriaArmadura categoria;

    @NotBlank(message = "O valor de CA é obrigatório.")
    private String valorCA;

    private Integer requisitoForca;

    @NotNull(message = "O campo de desvantagem em furtividade é obrigatório.")
    private Boolean desvantagemFurtividade;

    @NotNull(message = "O peso é obrigatório.")
    private Double peso;

    @NotBlank(message = "O custo é obrigatório.")
    private String custo;

    public ArmaduraDTO(Armadura armadura) {
        this.id = armadura.getId();
        this.nome = armadura.getNome();
        this.categoria = armadura.getCategoria();
        this.valorCA = armadura.getValorCA();
        this.requisitoForca = armadura.getRequisitoForca();
        this.desvantagemFurtividade = armadura.getDesvantagemFurtividade();
        this.peso = armadura.getPeso();
        this.custo = armadura.getCusto();
    }
}
