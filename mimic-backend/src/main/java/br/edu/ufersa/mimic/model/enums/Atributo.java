package br.edu.ufersa.mimic.model.enums;

import lombok.Getter;

@Getter
public enum Atributo {
    FORCA("Força"),
    DESTREZA("Destreza"),
    CONSTITUICAO("Constituição"),
    INTELIGENCIA("Inteligência"),
    SABEDORIA("Sabedoria"),
    CARISMA("Carisma");

    private final String nomeAtributo;

    Atributo(String nomeAtributo) {
        this.nomeAtributo = nomeAtributo;
    }

}
