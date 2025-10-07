package br.edu.ufersa.mimic.dto.equipamento;

import br.edu.ufersa.mimic.model.equipamento.Arma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.CategoriaArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoArma;
import br.edu.ufersa.mimic.model.enums.equipamento.arma.TipoDeDano;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ArmaDTO {

    private Long id;

    @NotBlank
    private String nome;
    private String descricao;
    @NotNull
    private CategoriaArma categoriaArma;
    @NotNull
    private TipoArma tipo;
    @NotNull
    private TipoDeDano tipoDeDano;
    @NotBlank
    private String dano;
    private Double peso;
    private String custo;
    private Set<String> propriedades;
    private String maestria;

    public ArmaDTO(Arma arma) {
        this.id = arma.getId();
        this.nome = arma.getNome();
        this.descricao = arma.getDescricao();
        this.categoriaArma = arma.getCategoriaArma();
        this.tipo = arma.getTipo();
        this.tipoDeDano = arma.getTipoDeDano();
        this.dano = arma.getDano();
        this.peso = arma.getPeso();
        this.custo = arma.getCusto();
        this.propriedades = arma.getPropriedades();
        this.maestria = arma.getMaestria();
    }
}
