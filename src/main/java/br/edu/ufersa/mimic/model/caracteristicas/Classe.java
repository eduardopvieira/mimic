package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.ClasseDTO;
import br.edu.ufersa.mimic.dto.caracteristicas.ClasseDTO;
import br.edu.ufersa.mimic.model.enums.Atributo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "classe_opcoes_pericias", joinColumns = @JoinColumn(name = "classe_id"))
    @Column(name = "pericia_opcao")
    private Set<String> opcoesDePericias;

    @Column(name = "quantidade_escolha_pericias", nullable = false)
    private Integer quantidadeEscolhaPericias;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaDeClasse> caracteristicas;

    @OneToMany(mappedBy = "classePai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subclasse> subclasses;

    @Column(name = "e_conjurador")
    private boolean isConjurador;

    @Enumerated(EnumType.STRING)
    @Column(name = "atributo_conjuracao")
    private Atributo atributoDeConjuracao;

    public Classe (ClasseDTO classeDTO) {
        this.id = classeDTO.getId();
        this.nome = classeDTO.getNome();
        this.descricao = classeDTO.getDescricao();
        this.dadoDeVida = classeDTO.getDadoDeVida();
        this.proficienciasArmaduras = classeDTO.getProficienciasArmaduras();
        this.proficienciasArmas = classeDTO.getProficienciasArmas();
        this.proficienciasTestesDeResistencia = classeDTO.getProficienciasTestesDeResistencia();
        this.opcoesDePericias = classeDTO.getOpcoesDePericias();
        this.quantidadeEscolhaPericias = classeDTO.getQuantidadeEscolhaPericias();
        this.isConjurador = classeDTO.isConjurador();
        if (classeDTO.getAtributoDeConjuracao() != null) {
            this.atributoDeConjuracao =classeDTO.getAtributoDeConjuracao();
        }
    }
}
