package br.edu.ufersa.mimic.api.dto.habilidades;

import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AcaoCriaturaDTO {
    public Long id;
    public String nome;
    public String descricao;

    public AcaoCriaturaDTO(AcaoCriatura entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
    }
}