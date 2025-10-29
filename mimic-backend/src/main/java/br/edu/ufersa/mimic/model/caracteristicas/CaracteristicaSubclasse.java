package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caracteristicas_subclasse")
@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaSubclasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "nivel_adquirido", nullable = false)
    private int nivelAdquirido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subclasse_id", nullable = false)
    private Subclasse subclasse;

}
