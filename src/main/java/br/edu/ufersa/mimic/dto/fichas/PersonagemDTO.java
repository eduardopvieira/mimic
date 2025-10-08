package br.edu.ufersa.mimic.dto;

import br.edu.ufersa.mimic.model.caracteristicas.Classe;
import br.edu.ufersa.mimic.model.caracteristicas.Origem;
import br.edu.ufersa.mimic.model.caracteristicas.Raca;
import br.edu.ufersa.mimic.model.caracteristicas.Subclasse;
import br.edu.ufersa.mimic.model.enums.Alinhamento;
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

    // --- Informações Básicas ---
    @NotBlank
    private String nomePersonagem;
    @NotNull
    private Integer nivel;
    private Integer pontosDeExperiencia;
    private Alinhamento alinhamento;

    // --- Relacionamentos (IDs) ---
    @NotNull
    private Long classeId;
    private Long subclasseId;
    @NotNull
    private Long especieId;
    @NotNull
    private Long antecedenteId;

    // --- Atributos ---
    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    // --- Status de Combate ---
    private Integer pontosDeVidaMaximos;
    private Integer pontosDeVidaAtuais;
    private Integer pontosDeVidaTemporarios;
    private Integer classeDeArmadura;
    private Integer iniciativa;
    private Integer deslocamento;
    private Integer percepcaoPassiva;

    // --- Recursos ---
    private String dadosDeVidaTotais;
    private Integer dadosDeVidaGastos;
    private boolean inspiracaoHeroica;

    // --- Proficiências ---
    private Set<String> proficienciasPericias;
    private Set<String> proficienciasTestesDeResistencia;

    // --- Listas de IDs para Relacionamentos ManyToMany ---
    private List<Long> inventarioIds;
    private Set<Long> talentosIds;
    private Set<Long> magiasPreparadasIds;

    // --- Dinheiro ---
    private Integer pc;
    private Integer pp;
    private Integer po;
    private Integer pl;

    // Construtor de conversão (Entidade -> DTO)
    public PersonagemDTO(Personagem personagem) {
        this.id = personagem.getId();
        this.nomePersonagem = personagem.getNomePersonagem();
        this.nivel = personagem.getNivel();
        this.pontosDeExperiencia = personagem.getPontosDeExperiencia();
        this.alinhamento = personagem.getAlinhamento();

        // Mapeia entidades para seus IDs
        if (personagem.getClasse() != null) this.classeId = personagem.getClasse().getId();
        if (personagem.getSubclasse() != null) this.subclasseId = personagem.getSubclasse().getId();
        if (personagem.getEspecie() != null) this.especieId = personagem.getEspecie().getId();
        if (personagem.getOrigem() != null) this.antecedenteId = personagem.getOrigem().getId();

        this.forca = personagem.getForca();
        this.destreza = personagem.getDestreza();
        this.constituicao = personagem.getConstituicao();
        this.inteligencia = personagem.getInteligencia();
        this.sabedoria = personagem.getSabedoria();
        this.carisma = personagem.getCarisma();
        this.pontosDeVidaMaximos = personagem.getPontosDeVidaMaximos();
        this.pontosDeVidaAtuais = personagem.getPontosDeVidaAtuais();
        this.pontosDeVidaTemporarios = personagem.getPontosDeVidaTemporarios();
        this.classeDeArmadura = personagem.getClasseDeArmadura();
        this.iniciativa = personagem.getIniciativa();
        this.deslocamento = personagem.getDeslocamento();
        this.percepcaoPassiva = personagem.getPercepcaoPassiva();
        this.dadosDeVidaTotais = personagem.getDadosDeVidaTotais();
        this.dadosDeVidaGastos = personagem.getDadosDeVidaGastos();
        this.inspiracaoHeroica = personagem.isInspiracaoHeroica();
        this.proficienciasPericias = personagem.getProficienciasPericias();
        this.proficienciasTestesDeResistencia = personagem.getProficienciasTestesDeResistencia();
        this.pc = personagem.getPc();
        this.pp = personagem.getPp();
        this.po = personagem.getPo();
        this.pl = personagem.getPl();

        // Mapeia listas de entidades para listas de IDs
        if (personagem.getInventario() != null) {
            this.inventarioIds = personagem.getInventario().stream().map(Item::getId).collect(Collectors.toList());
        }
        if (personagem.getTalentos() != null) {
            this.talentosIds = personagem.getTalentos().stream().map(Talento::getId).collect(Collectors.toSet());
        }
        if (personagem.getMagiasPreparadas() != null) {
            this.magiasPreparadasIds = personagem.getMagiasPreparadas().stream().map(Magia::getId).collect(Collectors.toSet());
        }
    }
}
