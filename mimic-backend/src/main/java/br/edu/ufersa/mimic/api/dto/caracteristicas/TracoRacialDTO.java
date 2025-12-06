package br.edu.ufersa.mimic.api.dto.caracteristicas;

import br.edu.ufersa.mimic.model.caracteristicas.TracoRacial;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class TracoRacialDTO {
    private Long id;
    private String nome;

    public TracoRacialDTO(TracoRacial entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}