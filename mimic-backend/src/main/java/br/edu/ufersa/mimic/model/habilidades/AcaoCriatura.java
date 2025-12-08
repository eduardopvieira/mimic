package br.edu.ufersa.mimic.model.habilidades;

import br.edu.ufersa.mimic.api.dto.habilidades.AcaoCriaturaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "acoes_criatura")
@Getter @Setter @NoArgsConstructor
public class AcaoCriatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public AcaoCriatura(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public AcaoCriatura(AcaoCriaturaDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
    }
}