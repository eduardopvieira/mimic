package br.edu.ufersa.mimic.model.enums;

public enum Alinhamento {
    LEAL_BOM("Leal e Bom"),
    NEUTRO_BOM("Neutro e Bom"),
    CAOTICO_BOM("Caótico e Bom"),
    LEAL_NEUTRO("Leal e Neutro"),
    VERDADEIRO_NEUTRO("Verdadeiro Neutro"),
    CAOTICO_NEUTRO("Caótico e Neutro"),
    LEAL_MAL("Leal e Mal"),
    NEUTRO_MAL("Neutro e Mal"),
    CAOTICO_MAL("Caótico e Mal");

    private final String alinhamento;

    Alinhamento(String alinhamento) {
        this.alinhamento = alinhamento;
    }

    public String getAlinhamento() {
        return alinhamento;
    }
}
