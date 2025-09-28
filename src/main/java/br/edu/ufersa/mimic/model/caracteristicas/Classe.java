package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.enums.NomeClasse;
import br.edu.ufersa.mimic.model.enums.Atributo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "classes")
@Getter
@Setter
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private NomeClasse nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "dado_de_vida", nullable = false)
    private Integer dadoDeVida;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classe_proficiencias_armaduras", joinColumns = @JoinColumn(name = "classe_id"))
    @Column(name = "proficiencia_armadura")
    private Set<String> proficienciasArmaduras; // Ex: "Armaduras Leves", "Escudos"

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classe_proficiencias_armas", joinColumns = @JoinColumn(name = "classe_id"))
    @Column(name = "proficiencia_arma")
    private Set<String> proficienciasArmas; // Ex: "Armas Simples", "Armas Marciais"

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classe_proficiencias_testes_resistencia", joinColumns = @JoinColumn(name = "classe_id"))
    @Column(name = "atributo_proficiente", nullable = false)
    private Set<String> proficienciasTestesDeResistencia; // Ex: "FORÇA", "CONSTITUIÇÃO"

    // --- OPÇÕES DE ESCOLHA DE PERÍCIAS ---
    // Lista de perícias que o jogador pode escolher ao pegar esta classe no nível 1
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classe_opcoes_pericias", joinColumns = @JoinColumn(name = "classe_id"))
    @Column(name = "pericia_opcao")
    private Set<String> opcoesDePericias;

    @Column(name = "quantidade_escolha_pericias", nullable = false)
    private Integer quantidadeEscolhaPericias;

    // --- HABILIDADES E SUBCLASSES (RELACIONAMENTOS) ---
    // Lista todas as características que a classe base ganha em diferentes níveis
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaDeClasse> caracteristicas;

    // Lista todas as subclasses disponíveis para esta classe principal
    @OneToMany(mappedBy = "classePai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subclasse> subclasses;

    // --- INFORMAÇÕES DE CONJURAÇÃO ---
    // Flag para identificar rapidamente se a classe usa magias
    @Column(name = "e_conjurador")
    private boolean isConjurador;

    // Atributo principal de conjuração (pode ser nulo para classes não-conjuradoras)
    @Enumerated(EnumType.STRING)
    @Column(name = "atributo_conjuracao")
    private Atributo atributoDeConjuracao;

}
