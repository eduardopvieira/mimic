package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caracteristicas_de_classe")
@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaDeClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nivel_adquirido", nullable = false)
    private Integer nivelAdquirido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subclasse_id")
    private Subclasse subclasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traco_id", nullable = false)
    private TracoRacial traco;

}
