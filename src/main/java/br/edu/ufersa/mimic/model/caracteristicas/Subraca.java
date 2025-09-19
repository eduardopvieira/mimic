package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subracas")
@Getter
@Setter
public class Subraca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY é geralmente preferível a AUTO
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raca_id", nullable = false)
    private Raca racaPrincipal;
}