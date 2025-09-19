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

    @Column(nullable = false, unique = true, length = 50)
    private String nome; // Ex: "Acólito", "Artesão"

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // 3 atributos que a origem permite aumentar
    @ElementCollection
    @CollectionTable(name = "origem_atributos_aumentados", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "atributo")
    private Set<String> aumentoAtributos;

    // talento de origem que essa origem concede
    @ManyToOne
    @JoinColumn(name = "talento_id", nullable = false)
    private Talento talentoDeOrigem;

    // 2 proficiencias em pericia concedida
    @ElementCollection
    @CollectionTable(name = "antecedente_proficiencias_pericias", joinColumns = @JoinColumn(name = "antecedente_id"))
    @Column(name = "pericia")
    private Set<String> proficienciasPericias;

    // proficiencia com ferramenta concedida
    @Column(name = "proficiencia_ferramenta", length = 100)
    private String proficienciaFerramenta;

    // equipamento inicial
    @Column(name = "equipamento_inicial", columnDefinition = "TEXT")
    private String equipamentoInicial;

}
