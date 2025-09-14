package br.edu.ufersa.mimic.model.enums;

public enum NomeEspecie {
    AASIMAR("Aasimar"),
    ANAO("Anão"),
    DRACONATO("Draconato"),
    ELFO("Elfo"),
    GNOMO("Gnomo"),
    GOLIAS("Golias"),
    HUMANO("Humano"),
    ORC("Orc"),
    PEQUENINO("Pequenino"),
    TIFERINO("Tiferino");

    private final String nomeEspecie;

    NomeEspecie(String nomeEspecie) {
        this.nomeEspecie = nomeEspecie;
    }

    public String getNomeEspecie() {
        return nomeEspecie;
    }
}
