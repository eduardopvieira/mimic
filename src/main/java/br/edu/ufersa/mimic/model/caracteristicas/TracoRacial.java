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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magia_concedida_id")
    private Magia magiaConcedida;

    public TracoRacial(TracoRacialDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
    }

    public TracoRacial(String nome, String descricao, Magia magiaConcedida) {
        this.nome = nome;
        this.descricao = descricao;
        this.magiaConcedida = magiaConcedida;
    }

    public void updateFromDTO(TracoRacialDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
    }
}
