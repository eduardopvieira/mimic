package br.edu.ufersa.mimic.model.enums.equipamento.arma;

public enum CategoriaArma {
    SIMPLES("Simples"),
    MARCIAL("Marcial");

    private final String nomeCategoriaArma;

    CategoriaArma(String nomeAtributo) {
        this.nomeCategoriaArma = nomeAtributo;
    }

    public String getNomeCategoriaArma() {
        return nomeCategoriaArma;
    }
}
