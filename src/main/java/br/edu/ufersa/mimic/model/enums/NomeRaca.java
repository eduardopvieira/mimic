package br.edu.ufersa.mimic.model.enums;

public enum NomeRaca {
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

    private final String nomeRaca;

    NomeRaca(String nomeRaca) {
        this.nomeRaca = nomeRaca;
    }

    public String getNomeRaca() {
        return nomeRaca;
    }
}
