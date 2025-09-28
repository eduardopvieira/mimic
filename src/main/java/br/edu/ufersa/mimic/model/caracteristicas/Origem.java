package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "origens")
@Getter
@Setter
public class Origem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ElementCollection
    @CollectionTable(name = "origem_atributos_sugeridos", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "atributo")
    private Set<String> atributosSugeridos;

    @ManyToOne
    @JoinColumn(name = "talento_id", nullable = false)
    private Talento talentoInicial;

    @ElementCollection
    @CollectionTable(name = "origem_proficiencias_pericia", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "pericia")
    private Set<String> proficienciasPericia;

    // cada origem concede proficiência em 1 ferramenta
    @Column(name = "proficiencia_ferramenta")
    private String proficienciaFerramenta;

    @Column(name = "equipamento_opcao_a", columnDefinition = "TEXT")
    private String equipamentoOpcaoA; // Ex: "Suprimentos de Calígrafo, Livro (orações)..."

    @Column(name = "equipamento_opcao_b_po")
    private Integer equipamentoOpcaoB_PO; // Ex: 50
}
