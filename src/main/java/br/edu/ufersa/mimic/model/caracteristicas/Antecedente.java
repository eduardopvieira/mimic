package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "antecedentes")
@Getter
@Setter
public class Antecedente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome; // Ex: "Acólito", "Artesão"

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Os três atributos que o antecedente permite aumentar [cite: 1381]
    @ElementCollection
    @CollectionTable(name = "antecedente_aumento_atributos", joinColumns = @JoinColumn(name = "antecedente_id"))
    @Column(name = "atributo")
    private Set<String> aumentoAtributos;

    // O Talento de Origem que este antecedente concede [cite: 550, 1381]
    @ManyToOne
    @JoinColumn(name = "talento_id", nullable = false)
    private Talento talentoDeOrigem;

    // As duas proficiências em perícias concedidas [cite: 550, 1381]
    @ElementCollection
    @CollectionTable(name = "antecedente_proficiencias_pericias", joinColumns = @JoinColumn(name = "antecedente_id"))
    @Column(name = "pericia")
    private Set<String> proficienciasPericias;

    // A proficiência com ferramenta concedida [cite: 550, 1381]
    @Column(name = "proficiencia_ferramenta", length = 100)
    private String proficienciaFerramenta;

    // As opções de equipamento inicial [cite: 553, 1381]
    @Column(name = "equipamento_inicial", columnDefinition = "TEXT")
    private String equipamentoInicial;

    // Getters e Setters...
}
