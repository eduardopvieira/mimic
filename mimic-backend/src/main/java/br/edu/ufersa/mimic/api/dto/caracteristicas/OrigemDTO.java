package br.edu.ufersa.mimic.api.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.enums.Atributo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrigemDTO {

    private Long id;

    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    private String descricao;

    @NotEmpty(message = "A origem deve permitir pelo menos um atributo.")
    private Set<Atributo> atributosPermitidos;

    @NotEmpty(message = "A origem deve conceder pelo menos uma proficiência em perícia.")
    private Set<String> pericias;

    private String ferramenta;

    private String equipamentoInicial;

    @NotNull(message = "O ID do talento inicial é obrigatório.")
    @Positive
    private Long talentoInicialId;

    private String nomeTalentoInicial;

    private Long usuarioId;

    public OrigemDTO(Origem origem) {
        this.id = origem.getId();
        this.nome = origem.getNome();
        this.descricao = origem.getDescricao();

        this.atributosPermitidos = origem.getAtributosPermitidos();
        this.pericias = origem.getPericias();
        this.ferramenta = origem.getFerramenta();
        this.equipamentoInicial = origem.getEquipamentoInicial();

        if (origem.getTalentoInicial() != null) {
            this.talentoInicialId = origem.getTalentoInicial().getId();
            this.nomeTalentoInicial = origem.getTalentoInicial().getNome();
        }

        if (origem.getUsuario() != null) {
            this.usuarioId = origem.getUsuario().getUsuarioId();
        }
    }
}