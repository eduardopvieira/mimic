package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.dto.caracteristicas.RacaDTO;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "racas")
@Getter
@Setter
@NoArgsConstructor
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Integer deslocamento;

    @Column(nullable = false, length = 20)
    private Tamanho tamanho;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "raca_tracos",
            joinColumns = @JoinColumn(name = "raca_id"),
            inverseJoinColumns = @JoinColumn(name = "traco_id"))
    private List<TracoRacial> tracosRaciais;

    public Raca(RacaDTO racaDTO) {
        this.id = racaDTO.getId();
        this.nome = racaDTO.getNome();
        this.deslocamento = racaDTO.getDeslocamento();
        this.tamanho = racaDTO.getTamanho();
    }

    public <T> Raca(String nome, int deslocamento, Tamanho tamanho, List<T> tracosRaciais) {
        this.nome = nome;
        this.deslocamento = deslocamento;
        this.tamanho = tamanho;
        this.tracosRaciais = (List<TracoRacial>) tracosRaciais;
    }
}
