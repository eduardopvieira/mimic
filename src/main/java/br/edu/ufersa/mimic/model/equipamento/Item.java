package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ItemDTO;
import br.edu.ufersa.mimic.model.fichas.Personagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToMany(mappedBy = "inventario")
    private List<Personagem> personagens;

    public Item (ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.nome = itemDTO.getNome();
        this.descricao = itemDTO.getDescricao();
        this.peso = itemDTO.getPeso();
        this.custo = itemDTO.getCusto();
    }

}
