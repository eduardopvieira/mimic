package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.enums.Atributo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "classes")
@Getter @Setter @NoArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(name = "dado_de_vida", nullable = false)
    private Integer dadoDeVida;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> proficienciasTexto;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Atributo> testesDeResistencia;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> periciasDeClasse;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaDeClasse> caracteristicas;

    @OneToMany(mappedBy = "classePai", cascade = CascadeType.ALL)
    private List<Subclasse> subclasses;

    @Column(name = "e_conjurador")
    private boolean isConjurador;

    @Enumerated(EnumType.STRING)
    @Column(name = "atributo_conjuracao")
    private Atributo atributoDeConjuracao;

    @Column(columnDefinition = "TEXT")
    private String equipamentoA;

    @Column(columnDefinition = "TEXT")
    private String equipamentoB;

}