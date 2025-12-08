package br.edu.ufersa.mimic.model.habilidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "acoes_lendarias")
@Getter @Setter @NoArgsConstructor
public class AcaoLendaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public AcaoLendaria(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}