package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.dto.equipamento.ArmaDTO;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.CategoriaArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoDeDano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "armas")
@NoArgsConstructor
public class Arma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column
    private String descricao;

    @Column(nullable = false)
    private CategoriaArma categoriaArma;

    @Column(nullable = false)
    private TipoArma tipo;

    @Column(nullable = false)
    private TipoDeDano tipoDeDano;

    @Column
    private String dano; // Ex: 1d6, 2d4

    @Column
    private Double peso;

    @Column
    private String custo; // 10 PO, 5 PP, 1 PC

    @ElementCollection
    @Column
    private Set<String> propriedades; // ex: leve, versátil, pesado

    @Column
    private String maestria; //drenar, trespassar

    public Arma(ArmaDTO armaDTO) {
        this.id = armaDTO.getId();
        this.nome = armaDTO.getNome();
        this.descricao = armaDTO.getDescricao();
        this.categoriaArma = armaDTO.getCategoriaArma();
        this.tipo = armaDTO.getTipo();
        this.tipoDeDano = armaDTO.getTipoDeDano();
        this.dano = armaDTO.getDano();
        this.peso = armaDTO.getPeso();
        this.custo = armaDTO.getCusto();
        this.propriedades = armaDTO.getPropriedades();
        this.maestria = armaDTO.getMaestria();
    }
}
