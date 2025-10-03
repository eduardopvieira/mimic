package br.edu.ufersa.mimic.dto.habilidades;

import br.edu.ufersa.mimic.model.enums.EscolaDeMagia;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class MagiaDTO {

    private Long id;

    @NotBlank(message = "O nome da magia é obrigatório.")
    private String nome;

    private String descricao;

    @NotNull(message = "O círculo da magia é obrigatório.")
    @Min(value = 0, message = "O círculo mínimo é 0 (truque).")
    @Max(value = 9, message = "O círculo máximo é 9.")
    private Integer circulo;

    @NotNull(message = "A escola de magia é obrigatória.")
    private EscolaDeMagia escolaDeMagia;

    @NotBlank(message = "O tempo de conjuração é obrigatório.")
    private String tempoConjuracao;

    private String alcance;

    private Set<String> componentes;

    @NotBlank(message = "A duração é obrigatória.")
    private String duracao;

    public MagiaDTO(Magia magia) {
        this.id = magia.getId();
        this.nome = magia.getNome();
        this.descricao = magia.getDescricao();
        this.circulo = magia.getCirculo();
        this.escolaDeMagia = magia.getEscolaDeMagia();
        this.tempoConjuracao = magia.getTempoConjuracao();
        this.alcance = magia.getAlcance();
        this.componentes = magia.getComponentes();
        this.duracao = magia.getDuracao();
    }
}
