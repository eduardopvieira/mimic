package br.edu.ufersa.mimic.api.dto.fichas;

import br.edu.ufersa.mimic.model.enums.Alinhamento;
import br.edu.ufersa.mimic.model.enums.Tamanho;
import br.edu.ufersa.mimic.model.fichas.Criatura;
import br.edu.ufersa.mimic.model.habilidades.AcaoCriatura;
import br.edu.ufersa.mimic.model.habilidades.HabilidadeCriatura;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
public class CriaturaDTO {

    private Long id;
    private String nome;
    private Tamanho tamanho;
    private String tipo;
    private String tag;
    private Alinhamento alinhamento; // Recebe String ("Leal e Bom"), converte no Service

    private String ca;
    private String pv;

    // Front manda separado, backend junta ou salva separado. Vamos juntar.
    private String deslBase;
    private String deslVoo;
    private String deslNatacao;
    private String deslocamentoTotal; // Campo de leitura

    private int str; // Front usa str/dex/con...
    private int dex;
    private int con;
    private int intelligence; // cuidado com nomes (int é reservado) -> usaremos inteligencia
    private int wis;
    private int cha;

    private String saves;
    private String skills;
    private String resistDano;
    private String imunidDano;
    private String imunidCond;
    private String sentidos;
    private String idiomas;
    private String nd;

    // IDs para salvar (Input)
    private List<Long> habilidadesIds;
    private List<Long> acoesIds;

    // Objetos para leitura (Output - para edição)
    private List<RecursoDTO> habilidadesDetalhadas;
    private List<RecursoDTO> acoesDetalhadas;

    private String legendaryActions;
    private String lairActions;

    private Long usuarioId;

    // Classe interna auxiliar para devolver ID+Nome+Descricao
    @Getter @Setter @NoArgsConstructor
    public static class RecursoDTO {
        private Long id;
        private String nome;
        private String descricao;

        public RecursoDTO(Long id, String n, String d) {
            this.id = id; this.nome = n; this.descricao = d;
        }
    }

    public CriaturaDTO(Criatura c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.tamanho = c.getTamanho();
        this.tipo = c.getTipo();
        this.tag = c.getTag();
        this.alinhamento = c.getAlinhamento();

        this.ca = c.getCa();
        this.pv = c.getPv();
        this.deslocamentoTotal = c.getDeslocamento();

        this.str = c.getForca();
        this.dex = c.getDestreza();
        this.con = c.getConstituicao();
        this.intelligence = c.getInteligencia();
        this.wis = c.getSabedoria();
        this.cha = c.getCarisma();

        this.saves = c.getSalvaguardas();
        this.skills = c.getPericias();
        this.resistDano = c.getResistencias();
        this.imunidDano = c.getImunidades();
        this.imunidCond = c.getImunidadesCondicao();
        this.sentidos = c.getSentidos();
        this.idiomas = c.getIdiomas();
        this.nd = c.getNd();

        this.legendaryActions = c.getAcoesLendarias();
        this.lairActions = c.getAcoesCovil();

        if (c.getHabilidades() != null) {
            this.habilidadesIds = c.getHabilidades().stream().map(HabilidadeCriatura::getId).collect(Collectors.toList());
            this.habilidadesDetalhadas = c.getHabilidades().stream()
                    .map(h -> new RecursoDTO(h.getId(), h.getNome(), h.getDescricao()))
                    .collect(Collectors.toList());
        }

        if (c.getAcoes() != null) {
            this.acoesIds = c.getAcoes().stream().map(AcaoCriatura::getId).collect(Collectors.toList());
            this.acoesDetalhadas = c.getAcoes().stream()
                    .map(a -> new RecursoDTO(a.getId(), a.getNome(), a.getDescricao()))
                    .collect(Collectors.toList());
        }

        if (c.getUsuario() != null) {
            this.usuarioId = c.getUsuario().getUsuarioId();
        }
    }
}