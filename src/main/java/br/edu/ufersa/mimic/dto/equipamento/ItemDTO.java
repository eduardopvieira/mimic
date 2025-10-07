package br.edu.ufersa.mimic.dto.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    private Long id;

    @NotBlank(message = "O nome do item é obrigatório.")
    private String nome;

    private String descricao;

    private Double peso;

    private String custo;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.descricao = item.getDescricao();
        this.peso = item.getPeso();
        this.custo = item.getCusto();
    }

}
