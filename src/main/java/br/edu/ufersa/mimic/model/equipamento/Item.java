package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ItemDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itens")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column
    private Double peso;

    @Column
    private String custo;

    public Item (ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.nome = itemDTO.getNome();
        this.descricao = itemDTO.getDescricao();
        this.peso = itemDTO.getPeso();
        this.custo = itemDTO.getCusto();
    }

}
