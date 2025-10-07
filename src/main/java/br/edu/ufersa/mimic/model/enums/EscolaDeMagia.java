package br.edu.ufersa.mimic.model.enums;

public enum EscolaDeMagia {
    ABJURACAO("Abjuração"),
    ADIVINHACAO("Adivinhação"),
    ENCANTAMENTO("Encantamento"),
    EVOCACAO("Evocação"),
    ILUSAO("Ilusão"),
    INVOCACAO("Invocação"),
    NECROMANCIA("Necromancia"),
    TRANSMUTACAO("Transmutação");

    private final String escolaDeMagia;

    EscolaDeMagia(String escolaDeMagia) {
        this.escolaDeMagia = escolaDeMagia;
    }

    public String getEscolaDeMagia() {
        return escolaDeMagia;
    }
}

