package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaduraDTO;
import br.edu.ufersa.mimic.model.enums.equipamento.armadura.CategoriaArmadura;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "armaduras")
@Getter
@Setter
@NoArgsConstructor
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
    private String custo;

    public Armadura(ArmaduraDTO armaduraDTO) {
        this.categoria = armaduraDTO.getCategoria();
        this.custo = armaduraDTO.getCusto();
        this.desvantagemFurtividade = armaduraDTO.getDesvantagemFurtividade();
        this.id = armaduraDTO.getId();
        this.nome = armaduraDTO.getNome();
        this.peso = armaduraDTO.getPeso();
        this.requisitoForca = armaduraDTO.getRequisitoForca();
        this.valorCA = armaduraDTO.getValorCA();
    }
}
