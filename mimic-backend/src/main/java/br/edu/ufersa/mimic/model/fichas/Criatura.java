package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criaturas")
@Getter @Setter @NoArgsConstructor
public class Criatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho; // MÉDIO, GRANDE, ETC.

    @Column(name = "tipo_criatura")
    private String tipo; // Ex: "Morto-vivo", "Humanoide (Elfo)"

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    // --- ESTATÍSTICAS DEFENSIVAS ---
    @Column(name = "classe_armadura")
    private Integer ca;

    @Column(name = "descricao_ca")
    private String descricaoCa; // Ex: "(Armadura Natural)" ou "(Cota de Malha)"

    @Column(name = "pontos_vida_total")
    private Integer pvTotal;

    @Column(name = "formula_vida")
    private String formulaVida; // Ex: "2d8 + 4"

    @Column
    private String deslocamento; // Ex: "9m, Voo 12m"

    // --- ATRIBUTOS ---
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    // --- PROFICIÊNCIAS E SENTIDOS (Strings para simplificar o DB) ---

    @Column(columnDefinition = "TEXT")
    private String salvaguardas; // Ex: "CON +4, SAB +2" (Nulo se não tiver)

    @Column(columnDefinition = "TEXT")
    private String pericias; // Ex: "Furtividade +6, Percepção +3"

    @Column(columnDefinition = "TEXT")
    private String vulnerabilidades; // Ex: "Fogo"

    @Column(columnDefinition = "TEXT")
    private String resistencias; // Ex: "Contundente de ataques não mágicos"

    @Column(columnDefinition = "TEXT")
    private String imunidades; // Ex: "Veneno"

    @Column(columnDefinition = "TEXT")
    private String imunidadesCondicao; // Ex: "Envenenado, Caído"

    @Column
    private String sentidos; // Ex: "Visão no escuro 18m, Percepção passiva 13"

    @Column
    private String idiomas; // Ex: "Comum, Élfico"

    // --- DESAFIO ---
    @Column(name = "nivel_desafio")
    private String nd; // String para aceitar frações: "1/4", "1/8"

    @Column
    private Integer xp; // Ex: 50, 200

    @Column(name = "bonus_proficiencia")
    private Integer bonusProficiencia; // Útil para cálculos internos (+2, +3...)

    // --- AÇÕES E TRAÇOS ---
    // Recomendação: Use Markdown ou HTML simples aqui para negrito e itálico.

    @Column(columnDefinition = "TEXT")
    private String tracos; // Passivas. Ex: "**Anfíbio.** O sapo pode respirar..."

    @Column(columnDefinition = "TEXT")
    private String acoes; // Ataques. Ex: "**Mordida.** *Ataque Corpo-a-Corpo:* +4..."

    @Column(columnDefinition = "TEXT")
    private String reacoes; // Ex: "**Aparar.** A criatura adiciona 2 na CA..."

    // (Opcional) Monstros Lendários têm ações lendárias
    @Column(columnDefinition = "TEXT")
    private String acoesLendarias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Pode ser nulo (Sistema)
    private Usuario usuario;

}