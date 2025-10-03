package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.TracoRacialDTO;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tracos_raciais")
@Getter
@Setter
@NoArgsConstructor
public class TracoRacial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    /**
     * Se este traço conceder uma magia/truque, este campo será preenchido.
     * É opcional (nullable).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magia_concedida_id")
    private Magia magiaConcedida;

    // Construtor e método de atualização para o padrão que estamos usando
    public TracoRacial(TracoRacialDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        // A associação com Magia será feita no Service
    }

    public void updateFromDTO(TracoRacialDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
    }
}
