package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "racas")
@Getter
@Setter
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false)
    private Integer deslocamento;

    @Column(nullable = false, length = 20)
    private String tamanho;

    @ElementCollection
    @CollectionTable(name = "raca_aumento_atributos", joinColumns = @JoinColumn(name = "raca_id"))
    @MapKeyColumn(name = "atributo", length = 20)
    @Column(name = "valor_aumento")
    private Map<String, Integer> aumentoAtributos;

    @ElementCollection
    @CollectionTable(name = "raca_idiomas", joinColumns = @JoinColumn(name = "raca_id"))
    @Column(name = "idioma", nullable = false)
    private Set<String> idiomas; // Ex: "Comum", "Anão"

    @ElementCollection
    @CollectionTable(name = "raca_proficiencias", joinColumns = @JoinColumn(name = "raca_id"))
    @Column(name = "proficiencia")
    private Set<String> proficienciasIniciais;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "raca_caracteristicas",
            joinColumns = @JoinColumn(name = "raca_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id"))
    private List<CaracteristicaRacial> caracteristicasRaciais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raca_pai_id")
    private Raca racaPai; // Se for uma sub-raça, aponta para a raça principal (ex: Alto Elfo -> Elfo)

    @OneToMany(mappedBy = "racaPai")
    private List<Raca> subracas; // Lista de sub-raças (ex: Elfo -> [Alto Elfo, Elfo da Floresta])

}
