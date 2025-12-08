package br.edu.ufersa.mimic.model.enums;

public enum Tamanho {
    MINUSCULO("Minúsculo"),
    PEQUENO("Pequeno"),
    MEDIO("Médio"),
    GRANDE("Grande"),
    ENORME("Enorme"),
    COLOSSAL("Colossal");

    private final String tamanho;

    Tamanho(String tamanho) {this.tamanho = tamanho;}

}
