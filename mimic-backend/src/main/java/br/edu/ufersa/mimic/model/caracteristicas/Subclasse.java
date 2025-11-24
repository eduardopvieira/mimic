package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subclasses")
@Getter @Setter @NoArgsConstructor
public class Subclasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Vai para o campo "Subclasse" no cabeçalho da ficha

    // RELACIONAMENTO COM CLASSE PAI
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classePai;

    // RELACIONAMENTO UNIFICADO
    // Ao invés de criar uma entidade nova para características de subclasse,
    // reutilizamos a tabela padrão.
    // O sistema vai buscar: "Características onde subclasse_id = ID desta subclasse"
    @OneToMany(mappedBy = "subclasse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaracteristicaDeClasse> caracteristicas;

}