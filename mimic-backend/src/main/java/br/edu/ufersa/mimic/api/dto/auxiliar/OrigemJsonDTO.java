package br.edu.ufersa.mimic.api.dto.auxiliar;

import br.edu.ufersa.mimic.model.enums.Atributo;
import lombok.Data;
import java.util.Set;

@Data
public class OrigemJsonDTO {
    private String nome;
    private String descricao;
    private Set<Atributo> atributosPermitidos;
    private String talentoInicial;
    private Set<String> pericias;
    private String ferramenta;
    private String equipamentoInicial;
    private String equipamentoA;
    private String equipamentoB;
}