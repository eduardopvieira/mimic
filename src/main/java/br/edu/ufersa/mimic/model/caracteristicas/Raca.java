package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "racas")
@Getter
@Setter
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer deslocamento;

    @Column(nullable = false, length = 20)
    private Tamanho tamanho;

    // Este é o campo mais importante. Ele conterá todas as habilidades
    // especiais, como "Visão no Escuro", "Ancestralidade Feérica", etc.
    // As antigas "sub-raças" são modeladas como escolhas dentro de um traço aqui.
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "raca_tracos",
            joinColumns = @JoinColumn(name = "raca_id"),
            inverseJoinColumns = @JoinColumn(name = "traco_id"))
    private List<Traco> tracos;

}