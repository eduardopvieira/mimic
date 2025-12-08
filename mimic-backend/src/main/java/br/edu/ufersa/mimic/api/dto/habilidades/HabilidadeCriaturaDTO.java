package br.edu.ufersa.mimic.api.dto.habilidades;

import br.edu.ufersa.mimic.model.habilidades.HabilidadeCriatura;

public class HabilidadeCriaturaDTO {
    public Long id;
    public String nome;
    public String descricao;

    public HabilidadeCriaturaDTO(HabilidadeCriatura entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.descricao = entity.getDescricao();
    }
}
