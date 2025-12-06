package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.model.enums.Tamanho;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "racas") // Pode manter o nome da tabela, mas na UI use "Espécie" (2024)
@Getter @Setter @NoArgsConstructor
public class Raca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Vai para o campo "Espécie"

    @Column(nullable = false)
    private Integer deslocamento; // Vai para o campo "Velocidade" (Ex: 9m ou 30ft)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tamanho tamanho; // Vai para o campo "Tamanho" (Médio/Pequeno)

    @OneToMany(mappedBy = "raca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subraca> subracas;


    @OneToMany(mappedBy = "raca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TracoRacial> tracosRaciais;

}