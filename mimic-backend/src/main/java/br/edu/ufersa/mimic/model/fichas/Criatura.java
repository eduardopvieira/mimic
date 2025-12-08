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

    @Lob
    @Column(name = "imagem", length = 10000000)
    private byte[] imagem;

    @Column(name = "tipo_criatura")
    private String tipo;

    @Column(name = "tag_criatura")
    private String tag;

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    @Column(name = "classe_armadura")
    private String ca;

    @Column(name = "pontos_vida")
    private String pv;

    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    private String salvaguardas;

    private String deslBase;
    private String deslVoo;
    private String deslNatacao;

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

    @Column(columnDefinition = "TEXT")
    private String acoesLendarias;

    @Column(columnDefinition = "TEXT")
    private String acoesCovil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}