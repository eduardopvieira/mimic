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
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaTalento categoria;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "pre_requisito")
    private String preRequisito;

    @Column(nullable = false)
    private boolean isRepetivel;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "talento_atributos", joinColumns = @JoinColumn(name = "talento_id"))
    @Column(name = "atributo_elegivel")
    private Set<Atributo> atributosElegiveis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

}