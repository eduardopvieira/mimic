package br.edu.ufersa.mimic.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.CaracteristicaDeClasse;
import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.enums.NomeClasse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClasseDTO {

    private Long id;

    @NotNull(message = "O nome da classe é obrigatório.")
    private NomeClasse nome;

    private String descricao;

    @NotNull(message = "O dado de vida é obrigatório.")
    @Positive
    private Integer dadoDeVida;

    private Set<String> proficienciasArmaduras;
    private Set<String> proficienciasArmas;
    private Set<String> proficienciasTestesDeResistencia;

    private Set<String> opcoesDePericias;

    @NotNull(message = "A quantidade de perícias a escolher é obrigatória.")
    private Integer quantidadeEscolhaPericias;

    private List<Long> caracteristicasIds;
    private List<Long> subclassesIds;

    private boolean isConjurador;
    private Atributo atributoDeConjuracao;

    public ClasseDTO(Classe classe) {
        this.id = classe.getId();
        this.nome = classe.getNome();
        this.descricao = classe.getDescricao();
        this.dadoDeVida = classe.getDadoDeVida();
        this.proficienciasArmaduras = classe.getProficienciasArmaduras();
        this.proficienciasArmas = classe.getProficienciasArmas();
        this.proficienciasTestesDeResistencia = classe.getProficienciasTestesDeResistencia();
        this.opcoesDePericias = classe.getOpcoesDePericias();
        this.quantidadeEscolhaPericias = classe.getQuantidadeEscolhaPericias();
        this.isConjurador = classe.isConjurador();
        this.atributoDeConjuracao = classe.getAtributoDeConjuracao();

        if (classe.getCaracteristicas() != null) {
            this.caracteristicasIds = classe.getCaracteristicas().stream()
                    .map(CaracteristicaDeClasse::getId)
                    .collect(Collectors.toList());
        }
        if (classe.getSubclasses() != null) {
            this.subclassesIds = classe.getSubclasses().stream()
                    .map(Subclasse::getId)
                    .collect(Collectors.toList());
        }
    }
}
