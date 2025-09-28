package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.dto.TracoDTO;
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

    public Traco(TracoDTO tracoDTO) {
        this.nome = tracoDTO.getNome();
        this.descricao = tracoDTO.getDescricao();
    }

}
