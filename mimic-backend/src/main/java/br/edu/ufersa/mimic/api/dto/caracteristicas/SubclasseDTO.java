package br.edu.ufersa.mimic.api.dto.caracteristicas; // Ajuste o pacote

import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class SubclasseDTO {
    private Long id;
    private String nome;

    public SubclasseDTO(Subclasse entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
    }
}