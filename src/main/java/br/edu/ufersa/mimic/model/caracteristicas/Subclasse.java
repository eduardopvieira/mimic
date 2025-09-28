package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subclasses")
@Getter
@Setter
public class Subclasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classePai;

    @OneToMany(mappedBy = "subclasse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaSubclasse> caracteristicas;

}
