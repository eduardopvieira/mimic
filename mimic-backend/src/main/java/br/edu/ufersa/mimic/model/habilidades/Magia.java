package br.edu.ufersa.mimic.model.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.MagiaDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.EscolaDeMagia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "magias")
@Getter @Setter @NoArgsConstructor
public class Magia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer circulo;

    @Enumerated(EnumType.STRING)
    @Column
    private EscolaDeMagia escolaDeMagia;

    @Column(nullable = false)
    private String tempoConjuracao;

    @Column
    private String alcance;

    @Column
    private String componentes;

    @Column
    private String duracao;

    @Column(name = "e_concentracao")
    private boolean isConcentracao;

    @Column(name = "e_ritual")
    private boolean isRitual;

    @Column(name = "formula_dano")
    private String formulaDano;

    @Column(name = "tipo_dano")
    private String tipoDano;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    public Magia (MagiaDTO magia) {
        this.id = magia.getId();
        this.nome = magia.getNome();
        this.descricao = magia.getDescricao();
        this.circulo = magia.getCirculo();
        this.escolaDeMagia = magia.getEscolaDeMagia();
        this.tempoConjuracao = magia.getTempoConjuracao();
        this.alcance = magia.getAlcance();
        this.componentes = magia.getComponentes();
        this.duracao = magia.getDuracao();
    }
}