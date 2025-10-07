package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.fichas.Personagem;
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

    // --- INFORMAÇÕES BÁSICAS ---
    private String nomePersonagem;
    private Integer nivel;
    private Integer pontosDeExperiencia;
    private Alinhamento alinhamento;

    // --- RELACIONAMENTOS (simplificados para DTO) ---
    private String nomeClasse;
    private String nomeSubclasse;
    private String nomeEspecie;
    private String nomeOrigem;

    // --- ATRIBUTOS PRINCIPAIS ---
    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    // --- STATUS DE COMBATE ---
    private Integer pontosDeVidaMaximos;
    private Integer pontosDeVidaAtuais;
    private Integer pontosDeVidaTemporarios;
    private Integer classeDeArmadura;
    private Integer iniciativa;
    private Integer deslocamento;
    private Integer percepcaoPassiva;

    // --- RECURSOS ---
    private String dadosDeVidaTotais;
    private Integer dadosDeVidaGastos;
    private boolean inspiracaoHeroica;

    // --- PROFICIÊNCIAS ---
    private Set<String> proficienciasPericias;
    private Set<String> proficienciasTestesDeResistencia;

    // --- INVENTÁRIO (simplificado) ---
    private List<String> inventario;
    private Integer pc; // Peças de Cobre
    private Integer pp; // Peças de Prata
    private Integer po; // Peças de Ouro
    private Integer pl; // Peças de Platina

    // --- TALENTOS E MAGIAS (simplificados) ---
    private Set<String> talentos;
    private Set<String> magiasPreparadas;

    /**
     * Construtor para converter a entidade Personagem em PersonagemDTO.
     * @param personagem A entidade a ser convertida.
     */
    public PersonagemDTO(Personagem personagem) {
        // Informações Básicas
        this.id = personagem.getId();
        this.nomePersonagem = personagem.getNomePersonagem();
        this.nivel = personagem.getNivel();
        this.pontosDeExperiencia = personagem.getPontosDeExperiencia();
        this.alinhamento = personagem.getAlinhamento();

        // Relacionamentos (pegando apenas os nomes para simplificar)
        if (personagem.getClasse() != null) {
            this.nomeClasse = personagem.getClasse().getNome();
        }
        if (personagem.getSubclasse() != null) {
            this.nomeSubclasse = personagem.getSubclasse().getNome();
        }
        if (personagem.getEspecie() != null) {
            this.nomeEspecie = personagem.getEspecie().getNome();
        }
        if (personagem.getOrigem() != null) {
            this.nomeOrigem = personagem.getOrigem().getNome();
        }

        // Atributos
        this.forca = personagem.getForca();
        this.destreza = personagem.getDestreza();
        this.constituicao = personagem.getConstituicao();
        this.inteligencia = personagem.getInteligencia();
        this.sabedoria = personagem.getSabedoria();
        this.carisma = personagem.getCarisma();

        // Status de Combate
        this.pontosDeVidaMaximos = personagem.getPontosDeVidaMaximos();
        this.pontosDeVidaAtuais = personagem.getPontosDeVidaAtuais();
        this.pontosDeVidaTemporarios = personagem.getPontosDeVidaTemporarios();
        this.classeDeArmadura = personagem.getClasseDeArmadura();
        this.iniciativa = personagem.getIniciativa();
        this.deslocamento = personagem.getDeslocamento();
        this.percepcaoPassiva = personagem.getPercepcaoPassiva();

        // Recursos
        this.dadosDeVidaTotais = personagem.getDadosDeVidaTotais();
        this.dadosDeVidaGastos = personagem.getDadosDeVidaGastos();
        this.inspiracaoHeroica = personagem.isInspiracaoHeroica();

        // Proficiências
        this.proficienciasPericias = personagem.getProficienciasPericias();
        this.proficienciasTestesDeResistencia = personagem.getProficienciasTestesDeResistencia();

        // Inventário
        this.inventario = personagem.getInventario().stream()
                .map(item -> item.getNome()) // Supondo que Item tem um método getNome()
                .collect(Collectors.toList());
        this.pc = personagem.getPc();
        this.pp = personagem.getPp();
        this.po = personagem.getPo();
        this.pl = personagem.getPl();

        // Talentos e Magias
        this.talentos = personagem.getTalentos().stream()
                .map(talento -> talento.getNome()) // Supondo que Talento tem um método getNome()
                .collect(Collectors.toSet());
        this.magiasPreparadas = personagem.getMagiasPreparadas().stream()
                .map(magia -> magia.getNome()) // Supondo que Magia tem um método getNome()
                .collect(Collectors.toSet());
    }
}
