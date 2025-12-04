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
    private Integer circulo; // 0 = Truque, 1-9 = Nível

    @Enumerated(EnumType.STRING)
    @Column
    private EscolaDeMagia escolaDeMagia; // Ex: EVOCACAO (Para preencher a escola na ficha)

    // DADOS DE CONJURAÇÃO (Coluna "Tempo")
    @Column(nullable = false)
    private String tempoConjuracao; // Ex: "1 Ação", "1 Ação Bônus"

    // ALCANCE (Coluna "Alcance/Área")
    @Column
    private String alcance; // Ex: "18m", "Toque", "Cone de 4,5m"

    // COMPONENTES (Coluna "Custo")
    // Simplificado para String para caber direto na ficha.
    // Ex: "V, S" ou "V, S, M (Diamante 50po)"
    @Column
    private String componentes;

    // DURAÇÃO
    @Column
    private String duracao; // Ex: "Instantânea", "1 minuto"

    // FLAGS PARA A COLUNA "NOTAS"
    @Column(name = "e_concentracao")
    private boolean isConcentracao; // Se true, o front adiciona "(C)" na ficha

    @Column(name = "e_ritual")
    private boolean isRitual; // Se true, o front adiciona "(R)" na ficha

    // DADOS PARA COMBATE (Página 1 - Ataques & Página 2 - Efeito)
    @Column(name = "formula_dano")
    private String formulaDano; // Ex: "1d10", "2d6" (Nulo se for magia de utilidade)

    @Column(name = "tipo_dano")
    private String tipoDano; // Ex: "Fogo", "Radiante", "Contundente"

    // TEXTO
    @Column(columnDefinition = "TEXT")
    private String descricao; // Texto completo para consulta no site

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true) // Pode ser nulo (Sistema)
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