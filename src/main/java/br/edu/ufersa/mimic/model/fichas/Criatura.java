package br.edu.ufersa.mimic.model.fichas;

import br.edu.ufersa.mimic.dto.fichas.CriaturaDTO;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "criaturas")
@Getter
@Setter
@NoArgsConstructor
public class Criatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;

    @Column(name = "tipo_criatura")
    private String tipo; // Ex: "Humanoide", "Fera", "Monstruosidade"

    @Enumerated(EnumType.STRING)
    private Alinhamento alinhamento;

    @Column(name = "classe_de_armadura")
    private Integer classeDeArmadura;

    @Column(name = "pontos_de_vida")
    private Integer pontosDeVida;

    @Column(name = "dados_de_vida")
    private String dadosDeVida; // Ex: "3d8 + 6"

    @Column
    private String deslocamento; // Ex: "9m, voo 12m"

    // Atributos
    private int forca = 10;
    private int destreza = 10;
    private int constituicao = 10;
    private int inteligencia = 10;
    private int sabedoria = 10;
    private int carisma = 10;

    // Perícias (Ex: "Percepção": 3, "Furtividade": 5)
    @ElementCollection
    @CollectionTable(name = "criatura_pericias", joinColumns = @JoinColumn(name = "criatura_id"))
    @MapKeyColumn(name = "pericia_nome")
    @Column(name = "bonus_valor")
    private Map<String, Integer> pericias;

    @ElementCollection
    @CollectionTable(name = "criatura_imunidades_dano", joinColumns = @JoinColumn(name = "criatura_id"))
    @Column(name = "imunidade")
    private Set<String> imunidadesDano;

    @ElementCollection
    @CollectionTable(name = "criatura_resistencias_dano", joinColumns = @JoinColumn(name = "criatura_id"))
    @Column(name = "resistencia")
    private Set<String> resistenciasDano;

    @ElementCollection
    @CollectionTable(name = "criatura_vulnerabilidades_dano", joinColumns = @JoinColumn(name = "criatura_id"))
    @Column(name = "vulnerabilidade")
    private Set<String> vulnerabilidadesDano;

    @Column
    private String sentidos; // Ex: "Visão no escuro 18m, Percepção passiva 13"

    @Column
    private String idiomas;

    @Column(name = "nivel_de_desafio")
    private String nivelDeDesafio; // String para "1/2", "1/4", etc.

    @Column(columnDefinition = "TEXT")
    private String tracosEspeciais; // Para habilidades passivas como "Resistência à Magia"

    @Column(columnDefinition = "TEXT")
    private String acoes; // Para Ações como "Ataques Múltiplos", "Mordida"

    @Column(columnDefinition = "TEXT")
    private String acoesBonus;

    @Column(columnDefinition = "TEXT")
    private String reacoes;

    public Criatura (CriaturaDTO dto) {
        this.nome = dto.getNome();
        this.tamanho = dto.getTamanho();
        this.tipo = dto.getTipo();
        this.alinhamento = dto.getAlinhamento();
        this.classeDeArmadura = dto.getClasseDeArmadura();
        this.pontosDeVida = dto.getPontosDeVida();
        this.dadosDeVida = dto.getDadosDeVida();
        this.deslocamento = dto.getDeslocamento();
        this.forca = dto.getForca();
        this.destreza = dto.getDestreza();
        this.constituicao = dto.getConstituicao();
        this.inteligencia = dto.getInteligencia();
        this.sabedoria = dto.getSabedoria();
        this.carisma = dto.getCarisma();
        this.pericias = dto.getPericias();
        this.imunidadesDano = dto.getImunidadesDano();
        this.resistenciasDano = dto.getResistenciasDano();
        this.vulnerabilidadesDano = dto.getVulnerabilidadesDano();
        this.sentidos = dto.getSentidos();
        this.idiomas = dto.getIdiomas();
        this.nivelDeDesafio = dto.getNivelDeDesafio();
        this.tracosEspeciais = dto.getTracosEspeciais();
        this.acoes = dto.getAcoes();
        this.acoesBonus = dto.getAcoesBonus();
        this.reacoes = dto.getReacoes();
    }
}
