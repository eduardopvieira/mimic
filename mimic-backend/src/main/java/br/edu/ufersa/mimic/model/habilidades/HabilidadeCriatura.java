package br.edu.ufersa.mimic.model.habilidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habilidades_criatura")
@Getter @Setter @NoArgsConstructor
public class HabilidadeCriatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public HabilidadeCriatura(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}