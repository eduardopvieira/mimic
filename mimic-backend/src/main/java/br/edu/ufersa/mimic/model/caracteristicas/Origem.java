package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Atributo; // Certifique-se de ter este Enum
import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "origens")
@Getter @Setter @NoArgsConstructor
public class Origem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Vai para o campo "Origem" no cabeçalho

    @Column(columnDefinition = "TEXT")
    private String descricao; // Tooltip ou info extra no front

    // MUDANÇA: Use Enum para facilitar a vida do React
    // Regra 2024: A origem te dá 3 opções de atributos, você escolhe 2 para subir.
    // O Front recebe [INTELIGENCIA, SABEDORIA, CARISMA] e desenha as opções pro usuário.
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "origem_atributos", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "atributo")
    private Set<Atributo> atributosPermitidos;

    // RELACIONAMENTO CRUCIAL
    // Vai preencher o campo "Talentos" na ficha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talento_id", nullable = false)
    private Talento talentoInicial;

    // PROFICIÊNCIAS
    // O front usa isso para marcar os checkboxes de perícia (Skills)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "origem_pericias", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "pericia")
    private Set<String> pericias;

    @Column(name = "proficiencia_ferramenta")
    private String ferramenta; // Vai para o box "Ferramentas"

    // EQUIPAMENTO
    // Simplificado para um texto único.
    // Ex: "Símbolo Sagrado, Livro de Orações, 10 velas..."
    // Vai para o box de Equipamentos na pág 2 (ou pág 1 se for resumido)
    @Column(columnDefinition = "TEXT")
    private String equipamentoInicial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true) // Pode ser nulo (Sistema)
    private Usuario usuario;

}