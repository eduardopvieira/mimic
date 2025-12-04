package br.edu.ufersa.mimic.api.dto.fichas;

import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CriaturaDTO {

    private Long id;

    @NotBlank
    private String nome;
    private Tamanho tamanho;
    private String tipo;
    private Alinhamento alinhamento;

    private Integer ca;
    private String descricaoCa;
    private Integer pvTotal;
    private String formulaVida;
    private String deslocamento;

    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;

    private String salvaguardas;
    private String pericias;
    private String vulnerabilidades;
    private String resistencias;
    private String imunidades;
    private String imunidadesCondicao;
    private String sentidos;
    private String idiomas;

    private String nd;
    private Integer xp;
    private Integer bonusProficiencia;

    private String tracos;
    private String acoes;
    private String reacoes;
    private String acoesLendarias;

    private Long usuarioId;

    public CriaturaDTO(Criatura criatura) {
        this.id = criatura.getId();
        this.nome = criatura.getNome();
        this.tamanho = criatura.getTamanho();
        this.tipo = criatura.getTipo();
        this.alinhamento = criatura.getAlinhamento();

        this.ca = criatura.getCa();
        this.descricaoCa = criatura.getDescricaoCa();
        this.pvTotal = criatura.getPvTotal();
        this.formulaVida = criatura.getFormulaVida();
        this.deslocamento = criatura.getDeslocamento();

        this.forca = criatura.getForca();
        this.destreza = criatura.getDestreza();
        this.constituicao = criatura.getConstituicao();
        this.inteligencia = criatura.getInteligencia();
        this.sabedoria = criatura.getSabedoria();
        this.carisma = criatura.getCarisma();

        this.salvaguardas = criatura.getSalvaguardas();
        this.pericias = criatura.getPericias();
        this.vulnerabilidades = criatura.getVulnerabilidades();
        this.resistencias = criatura.getResistencias();
        this.imunidades = criatura.getImunidades();
        this.imunidadesCondicao = criatura.getImunidadesCondicao();
        this.sentidos = criatura.getSentidos();
        this.idiomas = criatura.getIdiomas();

        this.nd = criatura.getNd();
        this.xp = criatura.getXp();
        this.bonusProficiencia = criatura.getBonusProficiencia();

        this.tracos = criatura.getTracos();
        this.acoes = criatura.getAcoes();
        this.reacoes = criatura.getReacoes();
        this.acoesLendarias = criatura.getAcoesLendarias();

        if (criatura.getUsuario() != null) {
            this.usuarioId = criatura.getUsuario().getUsuarioId();
        }
    }
}