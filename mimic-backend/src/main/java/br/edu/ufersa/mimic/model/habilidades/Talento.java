package br.edu.ufersa.mimic.model.habilidades;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "talentos")
@Getter @Setter @NoArgsConstructor
public class Talento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome; // Ex: "Iniciado em Magia", "Mestre de Armas Grandes"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaTalento categoria; // ORIGEM, GERAL, EPIC (Nível 19)

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao; // Texto completo para referência

    @Column(name = "pre_requisito")
    private String preRequisito; // Ex: "Nível 4, Carisma 13+"

    @Column(nullable = false)
    private boolean isRepetivel; // Ex: "Melhoria de Atributo" pode pegar várias vezes

    // D&D 2024 CRITICAL FEATURE:
    // Quase todo talento agora dá +1 em um atributo.
    // O sistema precisa dizer quais atributos esse talento permite aumentar.
    // O Front escolhe UM deles e soma na ficha.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "talento_atributos", joinColumns = @JoinColumn(name = "talento_id"))
    @Column(name = "atributo_elegivel")
    private Set<Atributo> atributosElegiveis;
    // Ex: Para o talento "Atleta", a lista seria [FORCA, DESTREZA]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true) // Pode ser nulo (Sistema)
    private Usuario usuario;

}