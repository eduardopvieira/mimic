package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.model.enums.equipamento.armadura.CategoriaArmadura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "armaduras")
@Getter
@Setter
public class Armadura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private CategoriaArmadura categoria;

    @Column(nullable = false)
    private String valorCA;

    @Column
    private Integer requisitoForca;

    @Column
    private Boolean desvantagemFurtividade;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private String custo; // 10 PO, 5 PP, 1 PC
}
