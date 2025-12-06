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

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    // CONEXÃO COM A RAÇA (Pode ser nulo se o traço pertencer a uma sub-raça)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raca_id", nullable = true)
    private Raca raca;

    // NOVA CONEXÃO COM A SUB-RAÇA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subraca_id", nullable = true)
    private Subraca subraca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magia_concedida_id")
    private Magia magiaConcedida;
}