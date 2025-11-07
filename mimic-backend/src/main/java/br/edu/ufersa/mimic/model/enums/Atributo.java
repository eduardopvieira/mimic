package br.edu.ufersa.mimic.model.enums;

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

    public String getNomeAtributo() {
        return nomeAtributo;
    }
}
