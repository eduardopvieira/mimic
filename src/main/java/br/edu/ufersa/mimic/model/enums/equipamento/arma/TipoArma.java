package br.edu.ufersa.mimic.model.enums.equipamento.arma;

public enum TipoArma {
    CORPO_A_CORPO("Corpo a Corpo"),
    A_DISTANCIA("A Distância");

    private final String nomeTipoArma;

    TipoArma(String nomeTipoArma) {
        this.nomeTipoArma = nomeTipoArma;
    }

    public String getNomeCategoriaArma() {
        return nomeTipoArma;
    }
}
