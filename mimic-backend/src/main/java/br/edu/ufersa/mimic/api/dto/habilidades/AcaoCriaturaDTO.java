package br.edu.ufersa.mimic.api.dto.habilidades;

import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AcaoCriaturaDTO {
    public String nome;
    public String descricao;

    public AcaoCriaturaDTO(AcaoCriatura entity) {
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
    }
}