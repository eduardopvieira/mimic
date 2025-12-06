package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.api.dto.caracteristicas.SubracaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subracas")
@Getter @Setter @NoArgsConstructor
public class Subraca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raca_id", nullable = false)
    private Raca raca;

    public Subraca(SubracaDTO dto) {
        this.id = dto.getId();
        this.nome = dto.getNome();
    }
}