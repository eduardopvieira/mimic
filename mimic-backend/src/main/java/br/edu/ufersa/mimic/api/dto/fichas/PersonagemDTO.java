package br.edu.ufersa.mimic.api.dto.fichas;

import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Atributo;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.equipamento.Item;
import br.edu.ufersa.mimic.model.fichas.Personagem;
import br.edu.ufersa.mimic.model.habilidades.Magia;
import br.edu.ufersa.mimic.model.habilidades.Talento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PersonagemDTO {

    private Long id;

    @NotBlank
    private String nomePersonagem;

    @NotNull
    private Integer nivel;

    private Integer pontosDeExperiencia;
    private Alinhamento alinhamento;
    private Tamanho tamanho;

    // --- RELACIONAMENTOS (IDs para Entrada/Input) ---
    @NotNull
    private Long classeId;
    private Long subclasseId;
    @NotNull
    private Long racaId;
    @NotNull
    private Long origemId;

    // --- CAMPOS DE LEITURA (Nomes para Exibição no Front) ---
    // Adicionados para facilitar a listagem sem precisar buscar objetos aninhados complexos
    private String classeNome;
    private String subclasseNome;
    private String racaNome;
    private String origemNome;

    // --- IMAGEM ---
    // O Jackson serializa byte[] automaticamente para String Base64 no JSON
    private byte[] imagem;

    // --- ATRIBUTOS ---
    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    // --- STATUS ---
    private Integer pontosDeVidaMaximos;
    private Integer pontosDeVidaAtuais;
    private Integer pontosDeVidaTemporarios;

    private Integer classeDeArmadura;
    private Integer iniciativa;
    private Integer deslocamento;
    private Integer percepcaoPassiva;

    private Integer dadosDeVidaGastos;
    private boolean inspiracaoHeroica;

    // --- LISTAS SIMPLES ---
    private Set<String> pericias;
    private Set<String> salvaguardas;

    private Atributo atributoChaveConjuracao;

    private String aparencia;
    private String historia;

    private String escolhaEquipamentoClasse;
    private String escolhaEquipamentoOrigem;

    // --- RELACIONAMENTOS LISTAS (IDs) ---
    private List<Long> inventarioIds;
    private Set<Long> talentosIds;
    private Set<Long> magiasPreparadasIds;

    // --- DINHEIRO ---
    private Integer pc;
    private Integer pp;
    private Integer po;
    private Integer pl;

    private Long usuarioId;

    public PersonagemDTO(Personagem p) {
        this.id = p.getId();
        this.nomePersonagem = p.getNome();
        this.nivel = p.getNivel();
        this.pontosDeExperiencia = p.getPontosDeExperiencia();
        this.alinhamento = p.getAlinhamento();
        this.tamanho = p.getTamanho();

        // Mapeamento Inteligente: Seta ID e Nome se o objeto existir
        if (p.getClasse() != null) {
            this.classeId = p.getClasse().getId();
            this.classeNome = p.getClasse().getNome();
        }
        if (p.getSubclasse() != null) {
            this.subclasseId = p.getSubclasse().getId();
            this.subclasseNome = p.getSubclasse().getNome();
        }
        if (p.getRaca() != null) {
            this.racaId = p.getRaca().getId();
            this.racaNome = p.getRaca().getNome();
        }
        if (p.getOrigem() != null) {
            this.origemId = p.getOrigem().getId();
            this.origemNome = p.getOrigem().getNome();
        }

        // Imagem
        this.imagem = p.getImagem();

        this.forca = p.getForca();
        this.destreza = p.getDestreza();
        this.constituicao = p.getConstituicao();
        this.inteligencia = p.getInteligencia();
        this.sabedoria = p.getSabedoria();
        this.carisma = p.getCarisma();

        this.pontosDeVidaMaximos = p.getVidaMax();
        this.pontosDeVidaAtuais = p.getVidaAtual();
        this.pontosDeVidaTemporarios = p.getVidaTemp();
        this.classeDeArmadura = p.getClasseDeArmadura();
        this.iniciativa = p.getIniciativa();
        this.deslocamento = p.getDeslocamento();
        this.percepcaoPassiva = p.getPercepcaoPassiva();

        this.escolhaEquipamentoClasse = p.getEscolhaEquipamentoClasse();
        this.escolhaEquipamentoOrigem = p.getEscolhaEquipamentoOrigem();

        this.dadosDeVidaGastos = p.getDadosDeVidaGastos();
        this.inspiracaoHeroica = p.isInspiracaoHeroica();

        this.pericias = p.getPericias();
        this.salvaguardas = p.getSalvaguardas();
        this.atributoChaveConjuracao = p.getAtributoChaveConjuracao();

        this.pc = p.getPc();
        this.pp = p.getPp();
        this.po = p.getPo();
        this.pl = p.getPl();

        if (p.getInventario() != null) {
            this.inventarioIds = p.getInventario().stream().map(Item::getId).collect(Collectors.toList());
        }
        if (p.getTalentos() != null) {
            this.talentosIds = p.getTalentos().stream().map(Talento::getId).collect(Collectors.toSet());
        }
        if (p.getMagiasPreparadas() != null) {
            this.magiasPreparadasIds = p.getMagiasPreparadas().stream().map(Magia::getId).collect(Collectors.toSet());
        }

        if (p.getUsuario() != null) {
            this.usuarioId = p.getUsuario().getUsuarioId();
        }
    }
}