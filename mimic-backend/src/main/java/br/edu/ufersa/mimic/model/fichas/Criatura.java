package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.api.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "criaturas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Criatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "tipo_criatura")
    private String tipo;

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    @Column(name = "classe_armadura")
    private Integer ca;

    @Column(name = "descricao_ca")
    private String descricaoCa;

    @Column(name = "pontos_vida_total")
    private Integer pvTotal;

    @Column(name = "formula_vida")
    private String formulaVida;

    @Column
    private String deslocamento;

    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;


    @Column(columnDefinition = "TEXT")
    private String salvaguardas;

    @Column(columnDefinition = "TEXT")
    private String pericias;

    @Column(columnDefinition = "TEXT")
    private String vulnerabilidades;

    @Column(columnDefinition = "TEXT")
    private String resistencias;

    @Column(columnDefinition = "TEXT")
    private String imunidades;

    @Column(columnDefinition = "TEXT")
    private String imunidadesCondicao;

    @Column
    private String sentidos;

    @Column
    private String idiomas;

    @Column(name = "nivel_desafio")
    private String nd;

    @Column
    private Integer xp;

    @Column(name = "bonus_proficiencia")
    private Integer bonusProficiencia;

    @Column(columnDefinition = "TEXT")
    private String tracos;

    @Column(columnDefinition = "TEXT")
    private String acoes;

    @Column(columnDefinition = "TEXT")
    private String reacoes;

    @Column(columnDefinition = "TEXT")
    private String acoesLendarias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Criatura(CriaturaDTO criaturaDTO) {
        this.id = criaturaDTO.getId();
        this.nome = criaturaDTO.getNome();
        this.tamanho = criaturaDTO.getTamanho();
        this.tipo = criaturaDTO.getTipo();
        this.alinhamento = criaturaDTO.getAlinhamento();

        this.ca = criaturaDTO.getCa();
        this.descricaoCa = criaturaDTO.getDescricaoCa();
        this.pvTotal = criaturaDTO.getPvTotal();
        this.formulaVida = criaturaDTO.getFormulaVida();
        this.deslocamento = criaturaDTO.getDeslocamento();

        this.forca = criaturaDTO.getForca();
        this.destreza = criaturaDTO.getDestreza();
        this.constituicao = criaturaDTO.getConstituicao();
        this.inteligencia = criaturaDTO.getInteligencia();
        this.sabedoria = criaturaDTO.getSabedoria();
        this.carisma = criaturaDTO.getCarisma();
    }

}