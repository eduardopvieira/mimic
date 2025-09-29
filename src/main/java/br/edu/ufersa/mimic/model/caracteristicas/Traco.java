package br.edu.ufersa.mimic.model.caracteristicas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tracos")
@Getter
@Setter
@NoArgsConstructor
public class Traco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    // --- COMPONENTES (COMPOSIÇÃO) ---

    // Se o traço oferecer uma escolha, este relacionamento será preenchido.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "mecanica_escolha_id", referencedColumnName = "id")
    private MecanicaDeEscolha escolha;

    // Outros componentes (ativável, concede magia, etc.) podem ser adicionados aqui.
}
