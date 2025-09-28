package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.dto.CaracteristicaDeClasseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caracteristicas_de_classe")
@Getter
@Setter
@NoArgsConstructor
public class CaracteristicaDeClasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nivel_adquirido", nullable = false)
    private Integer nivelAdquirido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subclasse_id")
    private Subclasse subclasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traco_id", nullable = false)
    private Traco traco;

    CaracteristicaDeClasse(CaracteristicaDeClasseDTO caracteristicaDeClasseDTO) {
        this.nivelAdquirido = caracteristicaDeClasseDTO.getNivelAdquirido();
        this.traco = new Traco();
        this.traco.setId(caracteristicaDeClasseDTO.getTracoId());
        if (caracteristicaDeClasseDTO.getClasseId() != null) {
            this.classe = new Classe();
            this.classe.setId(caracteristicaDeClasseDTO.getClasseId());
        }
        if (caracteristicaDeClasseDTO.getSubclasseId() != null) {
            this.subclasse = new Subclasse();
            this.subclasse.setId(caracteristicaDeClasseDTO.getSubclasseId());
        }
    }

}
