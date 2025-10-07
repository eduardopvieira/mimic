package br.edu.ufersa.mimic.model.enums.equipamento.arma;

public enum TipoDeDano {
    CORTANTE("Cortante"),
    PERFURANTE("Perfurante"),
    CONTUNDENTE("Contundente");

    private final String nomeTipoDeDano;

    TipoDeDano(String nomeTipoDeDano) {
        this.nomeTipoDeDano = nomeTipoDeDano;
    }

    public String getNomeTipoDeDano() {
        return nomeTipoDeDano;
    }
}
