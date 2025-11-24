package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caracteristicas_de_classe")
@Getter @Setter @NoArgsConstructor
public class CaracteristicaDeClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Ex: "Ataque Furtivo", "Canalizar Divindade"

    @Column(columnDefinition = "TEXT")
    private String descricao; // O texto explicativo da habilidade.

    @Column(name = "nivel_adquirido", nullable = false)
    private Integer nivelAdquirido; // Essencial para filtrar o que vai pra ficha baseado no nível do char.

    // RELACIONAMENTOS
    // Uma característica pertence a uma Classe...
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    // ...OU pertence a uma Subclasse.
    // (Geralmente é um ou outro. Se o campo for nulo, sabemos a quem pertence).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subclasse_id")
    private Subclasse subclasse;

    // DICA PRO FUTURO (Opcional):
    // Se alguma habilidade altera atributos diretamente (como o +2 de Força do Bárbaro no lv 20),
    // você poderia ter flags aqui, mas para "preencher PDF", apenas o texto basta.
}