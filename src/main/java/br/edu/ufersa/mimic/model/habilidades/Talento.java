package br.edu.ufersa.mimic.model.habilidades;

import br.edu.ufersa.mimic.dto.habilidades.TalentoDTO;
import br.edu.ufersa.mimic.model.enums.CategoriaTalento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "talentos")
@Getter
@Setter
@NoArgsConstructor
public class Talento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nome; // artifista, artesão, etc

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaTalento categoria; // talentos gerais, de origem, etc

    @Column(name = "pre_requisito")
    private String preRequisito; // nivel 4 ou superior, destreza 13 ou superior, etc

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao; // descricao do talento

    @Column(nullable = false)
    private boolean isRepetivel; // talento pode ser adquirido mais de 1 vez

    public Talento(TalentoDTO talentoDTO) {
        this.id = talentoDTO.getId();
        this.nome = talentoDTO.getNome();
        this.categoria = talentoDTO.getCategoria();
        this.preRequisito = talentoDTO.getPreRequisito();
        this.descricao = talentoDTO.getDescricao();
        this.isRepetivel = talentoDTO.getIsRepetivel();
    }

    public Talento(String nome, CategoriaTalento categoria, String preRequisito, String descricao, boolean isRepetivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.preRequisito = preRequisito;
        this.descricao = descricao;
        this.isRepetivel = isRepetivel;
    }

}
