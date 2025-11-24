package br.edu.ufersa.mimic.model.enums;

public enum TipoItem {
    ARMA,       // Espadas, Arcos -> Vai para tabela de Ataques
    ARMADURA,   // Couro, Placas -> Define a base da CA
    ESCUDO,     // Escudo de Madeira/Metal -> Soma na CA (+2)
    FERRAMENTA, // Kit de Ladrão, Alaúde -> Vai para o box "Ferramentas"
    ITEM        // Corda, Poção, Tocha -> Vai apenas para o Inventário
}