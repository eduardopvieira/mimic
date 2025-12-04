package br.edu.ufersa.mimic.api.dto.equipamento;

import br.edu.ufersa.mimic.model.enums.TipoDeDano;
import br.edu.ufersa.mimic.model.enums.TipoItem;
import br.edu.ufersa.mimic.model.equipamento.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ItemDTO {

    private Long id;
    private String nome;
    private TipoItem tipo;
    private String descricao;
    private Double peso;
    private String custo;

    private String dano;
    private TipoDeDano tipoDano;
    private String propriedades;
    private String maestria;
    private String distancia;

    private Integer caBase;
    private Boolean addDestreza;
    private Integer maxDestreza;
    private Integer requisitoForca;
    private Boolean desvantagemFurtividade;

    private Long usuarioId;

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.tipo = item.getTipo();
        this.descricao = item.getDescricao();
        this.peso = item.getPeso();
        this.custo = item.getCusto();

        this.dano = item.getDano();
        this.tipoDano = item.getTipoDano();
        this.propriedades = item.getPropriedades();
        this.maestria = item.getMaestria();
        this.distancia = item.getDistancia();

        this.caBase = item.getCaBase();
        this.addDestreza = item.getAddDestreza();
        this.maxDestreza = item.getMaxDestreza();
        this.requisitoForca = item.getRequisitoForca();
        this.desvantagemFurtividade = item.getDesvantagemFurtividade();

        if (item.getUsuario() != null) {
            this.usuarioId = item.getUsuario().getUsuarioId();
        } else {
            this.usuarioId = null;
        }
    }
}