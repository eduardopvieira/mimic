package br.edu.ufersa.mimic.api.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.enums.Atributo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClasseDTO {

    private Long id;

    @NotNull(message = "O nome da classe é obrigatório.")
    private String nome;

    @NotNull(message = "O dado de vida é obrigatório.")
    @Positive
    private Integer dadoDeVida;


    private Set<String> proficienciasTexto;

    private Set<Atributo> testesDeResistencia;


    private Set<String> periciasDeClasse;

    @NotNull
    private Integer quantidadeEscolhaPericias;

    private boolean isConjurador;
    private Atributo atributoDeConjuracao;

    public ClasseDTO(Classe classe) {
        this.id = classe.getId();
        this.nome = classe.getNome();
        this.dadoDeVida = classe.getDadoDeVida();

        this.proficienciasTexto = classe.getProficienciasTexto();
        this.testesDeResistencia = classe.getTestesDeResistencia();
        this.periciasDeClasse = classe.getPericiasDeClasse();

        this.isConjurador = classe.isConjurador();
        this.atributoDeConjuracao = classe.getAtributoDeConjuracao();
    }
}