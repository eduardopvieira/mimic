package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.dto.PersonagemDTO;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
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
@Getter
@Setter
@NoArgsConstructor
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- INFORMAÇÕES BÁSICAS ---
    @Column(name = "nome_personagem", nullable = false)
    private String nomePersonagem;

    private Integer nivel;
    private Integer pontosDeExperiencia;

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    // --- RELACIONAMENTOS DE ORIGEM E CLASSE ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subclasse_id")
    private Subclasse subclasse; // Pode ser nulo em níveis baixos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id")
    private Raca especie; // Usando nossa entidade Raca para representar a Espécie

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "antecedente_id")
    private Origem origem;

    // --- ATRIBUTOS PRINCIPAIS ---
    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    // --- STATUS DE COMBATE ---
    private Integer pontosDeVidaMaximos;
    private Integer pontosDeVidaAtuais;
    private Integer pontosDeVidaTemporarios;
    private Integer classeDeArmadura;
    private Integer iniciativa;
    private Integer deslocamento;
    private Integer percepcaoPassiva;

    // --- RECURSOS ---
    private String dadosDeVidaTotais; // Ex: "5d10"
    private Integer dadosDeVidaGastos;
    private boolean inspiracaoHeroica;

    // --- PROFICIÊNCIAS ---
    @ElementCollection
    @CollectionTable(name = "personagem_proficiencias_pericias", joinColumns = @JoinColumn(name = "personagem_id"))
    @Column(name = "pericia")
    private Set<String> proficienciasPericias;

    @ElementCollection
    @CollectionTable(name = "personagem_proficiencias_testes_resistencia", joinColumns = @JoinColumn(name = "personagem_id"))
    @Column(name = "teste_resistencia")
    private Set<String> proficienciasTestesDeResistencia;

    @ManyToMany
    @JoinTable(
            name = "personagem_inventario",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> inventario;

    private Integer pc; // Peças de Cobre
    private Integer pp; // Peças de Prata
    private Integer po; // Peças de Ouro
    private Integer pl; // Peças de Platina

    // --- TALENTOS E MAGIAS ---
    @ManyToMany
    @JoinTable(
            name = "personagem_talentos",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "talento_id")
    )
    private Set<Talento> talentos;

    @ManyToMany
    @JoinTable(
            name = "personagem_magias_preparadas",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "magia_id")
    )
    private Set<Magia> magiasPreparadas;

    public Personagem(PersonagemDTO dto) {
        // Informações Básicas
        this.id = dto.getId();
        this.nomePersonagem = dto.getNomePersonagem();
        this.nivel = dto.getNivel();
        this.pontosDeExperiencia = dto.getPontosDeExperiencia();
        this.alinhamento = dto.getAlinhamento();

        // Atributos
        this.forca = dto.getForca();
        this.destreza = dto.getDestreza();
        this.constituicao = dto.getConstituicao();
        this.inteligencia = dto.getInteligencia();
        this.sabedoria = dto.getSabedoria();
        this.carisma = dto.getCarisma();

        // Status de Combate
        this.pontosDeVidaMaximos = dto.getPontosDeVidaMaximos();
        this.pontosDeVidaAtuais = dto.getPontosDeVidaAtuais();
        this.pontosDeVidaTemporarios = dto.getPontosDeVidaTemporarios();
        this.classeDeArmadura = dto.getClasseDeArmadura();
        this.iniciativa = dto.getIniciativa();
        this.deslocamento = dto.getDeslocamento();
        this.percepcaoPassiva = dto.getPercepcaoPassiva();

        // Recursos
        this.dadosDeVidaTotais = dto.getDadosDeVidaTotais();
        this.dadosDeVidaGastos = dto.getDadosDeVidaGastos();
        this.inspiracaoHeroica = dto.isInspiracaoHeroica();

        // Proficiências
        this.proficienciasPericias = dto.getProficienciasPericias();
        this.proficienciasTestesDeResistencia = dto.getProficienciasTestesDeResistencia();

        // Inventário (dinheiro)
        this.pc = dto.getPc();
        this.pp = dto.getPp();
        this.po = dto.getPo();
        this.pl = dto.getPl();

    }
}
