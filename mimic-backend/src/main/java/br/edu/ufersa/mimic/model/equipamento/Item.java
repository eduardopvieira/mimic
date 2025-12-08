package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.TipoDeDano;
import br.edu.ufersa.mimic.model.enums.TipoItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itens")
@Getter @Setter @NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItem tipo;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Double peso;

    @Column
    private String custo;


    @Column
    private String dano;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dano")
    private TipoDeDano tipoDano;

    @Column
    private String propriedades;

    @Column
    private String maestria;

    @Column(name = "distancia_arremesso")
    private String distancia;


    @Column(name = "ca_base")
    private Integer caBase;

    @Column(name = "add_destreza")
    private Boolean addDestreza;

    @Column(name = "max_destreza")
    private Integer maxDestreza;

    @Column(name = "req_forca")
    private Integer requisitoForca;

    @Column(name = "desv_furtividade")
    private Boolean desvantagemFurtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

}