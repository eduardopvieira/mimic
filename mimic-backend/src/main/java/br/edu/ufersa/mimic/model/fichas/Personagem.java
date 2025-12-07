package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.caracteristicas.*;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Atributo;
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

    // --- CABEÇALHO ---
    private Integer nivel; // Essencial para Proficiência (+2, +3...)

    @Column(name = "xp")
    private Integer pontosDeExperiencia;

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    // --- RELACIONAMENTOS BASE ---
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
    @JoinColumn(name = "raca_id") // Ou especie_id conforme preferir
    private Raca raca;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origem_id")
    private Origem origem;

    // --- ATRIBUTOS (Core Stats) ---
    // O sistema calcula o modificador na hora de enviar pro Front/PDF
    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    // --- ESTATÍSTICAS DE COMBATE (Snapshot) ---
    // Mantemos salvo para permitir edits manuais do usuário

    private Integer vidaMax;
    private Integer vidaAtual;
    private Integer vidaTemp;

    @Column(name = "ca_total")
    private Integer classeDeArmadura; // O Front sugere (10+DES...), o usuário confirma.

    private Integer iniciativa;       // O Front sugere (DES), o usuário confirma.
    private Integer deslocamento;     // Vem da Raça, mas o usuário pode ter botas mágicas.
    private Integer percepcaoPassiva; // 10 + WIS + Prof.

    // --- RECURSOS ---
    private Integer dadosDeVidaGastos; // O total é calculado pelo Nível + Classe
    private boolean inspiracaoHeroica;

    // --- PROFICIÊNCIAS (Strings simples para o PDF) ---
    // Ex: "Acrobacia", "Furtividade"
    @ElementCollection
    @CollectionTable(name = "personagem_pericias", joinColumns = @JoinColumn(name = "personagem_id"))
    private Set<String> pericias;

    // Ex: "FORCA", "CONSTITUICAO" (Vem da Classe, mas talentos podem dar mais)
    @ElementCollection
    @CollectionTable(name = "personagem_saves", joinColumns = @JoinColumn(name = "personagem_id"))
    private Set<String> salvaguardas;

    // --- INVENTÁRIO & DINHEIRO ---
    // Usa a classe Item Unificada que criamos
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "personagem_inventario",
            joinColumns = @JoinColumn(name = "personagem_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> inventario;

    private Integer pc, pp, po, pl; // Moedas

    // --- HABILIDADES & MAGIAS ---

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personagem_talentos")
    private Set<Talento> talentos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personagem_magias")
    private Set<Magia> magiasPreparadas;

    // Adicione dois campos string simples
    @Column(length = 1)
    private String escolhaEquipamentoClasse; // "A" ou "B"

    @Column(length = 1)
    private String escolhaEquipamentoOrigem; // "A" ou "B"

    // Para o cabeçalho da página de magias
    @Enumerated(EnumType.STRING)
    private Atributo atributoChaveConjuracao;

    // --- TEXTOS LIVRES (Opcional, mas útil pro PDF) ---
    @Column(columnDefinition = "TEXT")
    private String aparencia;

    @Column(columnDefinition = "TEXT")
    private String historia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Pode ser nulo (Sistema)
    private Usuario usuario;
}