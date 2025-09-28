package br.edu.ufersa.mimic.model.habilidades;

import br.edu.ufersa.mimic.dto.MagiaDTO;
import br.edu.ufersa.mimic.model.enums.EscolaDeMagia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "magias")
@Getter
@Setter
public class Magia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column(nullable = false)
    private Integer circulo; //0 para truques, 1-9 para magias

    @Column
    private EscolaDeMagia escolaDeMagia;

    @Column(nullable = false)
    private String tempoConjuracao; //1 ação, ação bonus, 1 minuto, etc

    @Column
    private String alcance; //toque, pessoal, 3 metros, etc.

    @ElementCollection
    @Column
    private Set<String> componentes; //valores sao V, S, M. pode ser 1 ou os 3

    @Column
    private String duracao; //instantanea, concentração, 1 minuto, 2 minutos...

    public Magia() {}

    public Magia(MagiaDTO magiaDTO) {
        this.id = magiaDTO.getId();
        this.nome = magiaDTO.getNome();
        this.descricao = magiaDTO.getDescricao();
        this.circulo = magiaDTO.getCirculo();
        this.escolaDeMagia = magiaDTO.getEscolaDeMagia();
        this.tempoConjuracao = magiaDTO.getTempoConjuracao();
        this.alcance = magiaDTO.getAlcance();
        this.componentes = magiaDTO.getComponentes();
        this.duracao = magiaDTO.getDuracao();
    }


}
