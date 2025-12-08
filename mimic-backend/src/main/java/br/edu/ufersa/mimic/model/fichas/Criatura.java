package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import br.edu.ufersa.mimic.model.habilidades.HabilidadeCriatura;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "criaturas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Criatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "tipo_criatura")
    private String tipo;

    @Column(name = "tag_criatura") // Adicionado tag que tem no front
    private String tag;

    // No front vc envia string "Leal e Bom", o Jackson converte se o Enum bater,
    // ou usamos String se preferir simplificar. Vou manter Enum.
    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    // --- COMBATE ---
    @Column(name = "classe_armadura")
    private String ca; // Mudei para String pois no front vc aceita "17 (Natural)"

    @Column(name = "pontos_vida") // Simplificado para bater com o front
    private String pv; // String para aceitar "136 (16d10 + 48)"

    // Deslocamentos (Separados ou String única? No front tem 3 campos)
    // Vou guardar consolidado ou criar campos.
    // Para simplificar, vou concatenar no Service ou guardar como JSON/Texto.
    // Mas baseada na sua classe antiga, vou manter 'deslocamento' como String geral.
    private String deslocamento;

    // --- ATRIBUTOS ---
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    // --- PROFICIÊNCIAS (Textos livres do passo 4) ---
    @Column(columnDefinition = "TEXT")
    private String salvaguardas;

    @Column(columnDefinition = "TEXT")
    private String pericias;

    @Column(columnDefinition = "TEXT")
    private String vulnerabilidades; // Não tem no front, mas mantive

    @Column(columnDefinition = "TEXT")
    private String resistencias; // "resistDano" no front

    @Column(columnDefinition = "TEXT")
    private String imunidades; // "imunidDano" no front

    @Column(columnDefinition = "TEXT")
    private String imunidadesCondicao;

    @Column
    private String sentidos;

    @Column
    private String idiomas;

    @Column(name = "nivel_desafio")
    private String nd;

    // --- LISTAS DINÂMICAS (Habilidades e Ações) ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "criatura_habilidades_rel",
            joinColumns = @JoinColumn(name = "criatura_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private List<HabilidadeCriatura> habilidades = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "criatura_acoes_rel",
            joinColumns = @JoinColumn(name = "criatura_id"),
            inverseJoinColumns = @JoinColumn(name = "acao_id")
    )
    private List<AcaoCriatura> acoes = new ArrayList<>();

    // --- TEXTOS FINAIS ---
    @Column(columnDefinition = "TEXT")
    private String acoesLendarias; // CORRIGIDO: String para o textarea

    @Column(columnDefinition = "TEXT")
    private String acoesCovil; // ADICIONADO: Para o textarea "lairActions"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}