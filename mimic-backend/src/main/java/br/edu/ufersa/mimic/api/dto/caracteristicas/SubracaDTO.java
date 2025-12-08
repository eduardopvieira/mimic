package br.edu.ufersa.mimic.api.dto.caracteristicas; // Ajuste o pacote

import br.edu.ufersa.mimic.model.caracteristicas.Subraca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class SubracaDTO {
    private Long id;
    private String nome;

    public SubracaDTO(Subraca entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }

}