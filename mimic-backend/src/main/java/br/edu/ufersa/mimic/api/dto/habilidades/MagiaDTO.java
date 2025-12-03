package br.edu.ufersa.mimic.api.dto;

import br.edu.ufersa.mimic.model.enums.EscolaDeMagia;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MagiaDTO {

    private Long id;
    private String nome;
    private Integer circulo;
    private EscolaDeMagia escola;
    private String tempoConjuracao;
    private String alcance;
    private String componentes;
    private String duracao;
    private boolean isConcentracao;
    private boolean isRitual;
    private String formulaDano;
    private String tipoDano;
    private String descricao;
    private Long usuarioId;

    public MagiaDTO(Magia entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.circulo = entity.getCirculo();
        this.escola = entity.getEscola();
        this.tempoConjuracao = entity.getTempoConjuracao();
        this.alcance = entity.getAlcance();
        this.componentes = entity.getComponentes();
        this.duracao = entity.getDuracao();
        this.isConcentracao = entity.isConcentracao();
        this.isRitual = entity.isRitual();
        this.formulaDano = entity.getFormulaDano();
        this.tipoDano = entity.getTipoDano();
        this.descricao = entity.getDescricao();
        if (entity.getUsuario() != null) {
            this.usuarioId = entity.getUsuario().getId();
        }
    }
}