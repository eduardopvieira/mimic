package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "personagens")
@Getter @Setter @NoArgsConstructor
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_personagem", nullable = false)
    private String nome;

    private Integer nivel;

    @Column(name = "xp")
    private Integer pontosDeExperiencia;

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subclasse_id")
    private Subclasse subclasse;

    @Lob
    @Column(name = "imagem", length = 10000000)
    private byte[] imagem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "raca_id")
    private Raca raca;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subraca_id")
    private Subraca subraca;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origem_id")
    private Origem origem;

    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;


    private Integer vidaMax;
    private Integer vidaAtual;
    private Integer vidaTemp;

    @Column(name = "ca_total")
    private Integer classeDeArmadura;

    private Integer iniciativa;
    private Integer deslocamento;
    private Integer percepcaoPassiva;

    private Integer dadosDeVidaGastos;
    private boolean inspiracaoHeroica;

    @ElementCollection
    @CollectionTable(name = "personagem_pericias", joinColumns = @JoinColumn(name = "personagem_id"))
    private Set<String> pericias;

    @ElementCollection
    @CollectionTable(name = "personagem_saves", joinColumns = @JoinColumn(name = "personagem_id"))
    private Set<String> salvaguardas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personagem_inventario",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> inventario;

    private Integer pc, pp, po, pl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personagem_talentos")
    private Set<Talento> talentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personagem_magias")
    private Set<Magia> magiasPreparadas;


    @Column(length = 1)
    private String escolhaEquipamentoClasse;

    @Column(length = 1)
    private String escolhaEquipamentoOrigem;

    @Enumerated(EnumType.STRING)
    private Atributo atributoChaveConjuracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}