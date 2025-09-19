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
    private String nome; // Ex: "Elfo", "Anão"

    // ... todos os outros campos (deslocamento, tamanho, etc.) permanecem iguais ...
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
    private Set<String> idiomas;

    @ElementCollection
    @CollectionTable(name = "raca_proficiencias", joinColumns = @JoinColumn(name = "raca_id"))
    @Column(name = "proficiencia")
    private Set<String> proficienciasIniciais;

    // Este campo de características continua o mesmo
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "raca_tracos",
            joinColumns = @JoinColumn(name = "raca_id"),
            inverseJoinColumns = @JoinColumn(name = "traco_id"))
    private List<Traco> tracosRaciais; // Supondo que você renomeou para Traço

    @OneToMany(mappedBy = "racaPrincipal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subraca> subracas;
}