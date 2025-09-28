package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrigemDTO {

    @NotBlank(message = "O nome não pode ser vazio ou nulo.")
    private String nome;

    private String descricao;

    @NotEmpty(message = "A origem deve sugerir pelo menos um atributo.")
    private Set<String> atributosSugeridos;

    @NotNull(message = "O ID do talento inicial não pode ser nulo.")
    @Positive(message = "O ID do talento inicial deve ser um número positivo.")
    private Long talentoInicialId; //id do talnto, nao eh o objeto

    @NotEmpty(message = "A origem deve conceder pelo menos uma proficiência em perícia.")
    private Set<String> proficienciasPericia;

    @NotBlank(message = "A proficiência em ferramenta não pode ser vazia.")
    private String proficienciaFerramenta;

    private String equipamentoOpcaoA;

    @NotNull(message = "A opção B de equipamento (valor em PO) não pode ser nula.")
    private Integer equipamentoOpcaoB_PO;

    public static OrigemDTO toResponseDTO(Origem origem) {
        OrigemDTO dto = new OrigemDTO();
        dto.setId(origem.getId());
        dto.setNome(origem.getNome());
        dto.setDescricao(origem.getDescricao());
        dto.setAtributosSugeridos(origem.getAtributosSugeridos());
        dto.setProficienciasPericia(origem.getProficienciasPericia());
        dto.setProficienciaFerramenta(origem.getProficienciaFerramenta());
        dto.setEquipamentoOpcaoA(origem.getEquipamentoOpcaoA());
        dto.setEquipamentoOpcaoB_PO(origem.getEquipamentoOpcaoB_PO());

        if (origem.getTalentoInicial() != null) {
            TalentoDTO talentoDTO = new TalentoDTO();
            talentoDTO.setId(origem.getTalentoInicial().getId());
            talentoDTO.setNome(origem.getTalentoInicial().getNome());
            dto.setTalentoInicial(talentoDTO);
        }

        return dto;
    }
}