package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.habilidades.Magia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tracos_raciais")
@Getter @Setter @NoArgsConstructor
public class TracoRacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Removi o unique=true
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    // CONEXÃO COM A RAÇA (Essencial para o OneToMany da classe Raca funcionar)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raca_id", nullable = false)
    private Raca raca;

    // Conexão opcional para preencher a página de magias automaticamente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magia_concedida_id")
    private Magia magiaConcedida;

}