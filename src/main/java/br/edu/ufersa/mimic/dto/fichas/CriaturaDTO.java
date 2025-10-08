package br.edu.ufersa.mimic.dto.fichas;

import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CriaturaDTO {

    private Long id;
    @NotBlank private String nome;
    private Tamanho tamanho;
    private String tipo;
    private Alinhamento alinhamento;
    private Integer classeDeArmadura;
    private Integer pontosDeVida;
    private String dadosDeVida;
    private String deslocamento;
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;
    private Map<String, Integer> pericias;
    private Set<String> imunidadesDano;
    private Set<String> resistenciasDano;
    private Set<String> vulnerabilidadesDano;
    private String sentidos;
    private String idiomas;
    private String nivelDeDesafio;
    private String tracosEspeciais;
    private String acoes;
    private String acoesBonus;
    private String reacoes;

    public CriaturaDTO(Criatura criatura) {
        this.id = criatura.getId();
        this.nome = criatura.getNome();
        this.tamanho = criatura.getTamanho();
        this.tipo = criatura.getTipo();
        this.alinhamento = criatura.getAlinhamento();
        this.classeDeArmadura = criatura.getClasseDeArmadura();
        this.pontosDeVida = criatura.getPontosDeVida();
        this.dadosDeVida = criatura.getDadosDeVida();
        this.deslocamento = criatura.getDeslocamento();
        this.forca = criatura.getForca();
        this.destreza = criatura.getDestreza();
        this.constituicao = criatura.getConstituicao();
        this.inteligencia = criatura.getInteligencia();
        this.sabedoria = criatura.getSabedoria();
        this.carisma = criatura.getCarisma();
        this.pericias = criatura.getPericias();
        this.imunidadesDano = criatura.getImunidadesDano();
        this.resistenciasDano = criatura.getResistenciasDano();
        this.vulnerabilidadesDano = criatura.getVulnerabilidadesDano();
        this.sentidos = criatura.getSentidos();
        this.idiomas = criatura.getIdiomas();
        this.nivelDeDesafio = criatura.getNivelDeDesafio();
        this.tracosEspeciais = criatura.getTracosEspeciais();
        this.acoes = criatura.getAcoes();
        this.acoesBonus = criatura.getAcoesBonus();
        this.reacoes = criatura.getReacoes();
    }
}
