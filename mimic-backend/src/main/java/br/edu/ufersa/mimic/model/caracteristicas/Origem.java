package br.edu.ufersa.mimic.model.caracteristicas;

import br.edu.ufersa.mimic.api.dto.caracteristicas.OrigemDTO;
import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "origens")
@Getter @Setter @NoArgsConstructor
public class Origem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "origem_atributos", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "atributo")
    private Set<Atributo> atributosPermitidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "talento_id", nullable = false)
    private Talento talentoInicial;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "origem_pericias", joinColumns = @JoinColumn(name = "origem_id"))
    @Column(name = "pericia")
    private Set<String> pericias;

    @Column(name = "proficiencia_ferramenta")
    private String ferramenta;

    @Column(columnDefinition = "TEXT")
    private String equipamentoA;

    @Column(columnDefinition = "TEXT")
    private String equipamentoB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    public Origem(OrigemDTO dto) {
        this.id = dto.getId();
        this.atualizarDados(dto);
    }

    public void atualizarDados(OrigemDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.atributosPermitidos = dto.getAtributosPermitidos();
        this.pericias = dto.getPericias();
        this.ferramenta = dto.getFerramenta();
        this.equipamentoA = dto.getEquipamentoA();
        this.equipamentoB = dto.getEquipamentoB();

        if (dto.getTalentoInicialId() != null) {
            Talento t = new Talento();
            t.setId(dto.getTalentoInicialId());
            this.talentoInicial = t;
        }

        if (dto.getUsuarioId() != null) {
            Usuario u = new Usuario();
            u.setUsuarioId(dto.getUsuarioId());
            this.usuario = u;
        } else {
            this.usuario = null;
        }
    }
}