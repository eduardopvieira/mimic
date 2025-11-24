package br.edu.ufersa.mimic.model.equipamento;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.TipoDeDano;
import br.edu.ufersa.mimic.model.enums.TipoItem; // ARMA, ARMADURA, ITEM, FERRAMENTA, OUTRO
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
    private TipoItem tipo; // O Discriminador (ARMA, ARMADURA, ITEM, etc.)

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Double peso; // Em kg ou lb (conforme sua regra)

    @Column
    private String custo; // String para aceitar "10 PO", "5 PP"

    // --- CAMPOS DE ARMA (Ignorados se tipo != ARMA) ---

    @Column
    private String dano; // Ex: "1d8", "2d6"

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dano")
    private TipoDeDano tipoDano; // Enum ajuda a validar, mas pode ser String se quiser simplificar

    @Column
    private String propriedades; // Simplifiquei o Set para String. Ex: "Leve, Versátil (1d10)"

    @Column
    private String maestria; // Ex: "Derrubar" (Campo novo D&D 2024!)

    @Column(name = "distancia_arremesso")
    private String distancia; // Ex: "6/18"

    // --- CAMPOS DE ARMADURA (Ignorados se tipo != ARMADURA) ---

    @Column(name = "ca_base")
    private Integer caBase; // Ex: 11, 13, 18

    @Column(name = "add_destreza")
    private Boolean addDestreza; // Se soma DES na CA (Leves/Medias)

    @Column(name = "max_destreza")
    private Integer maxDestreza; // Limite de DES (Média = 2, Pesada = 0, Leve = 99)

    @Column(name = "req_forca")
    private Integer requisitoForca; // Ex: 13 ou 15

    @Column(name = "desv_furtividade")
    private Boolean desvantagemFurtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true) // Pode ser nulo (Sistema)
    private Usuario usuario;

}