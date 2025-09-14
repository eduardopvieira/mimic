package br.edu.ufersa.mimic.model.enums;

public enum NomeClasse {
    BARBARO("Bárbaro"),
    BARDO("Bardo"),
    BRUXO("Bruxo"),
    CLERIGO("Clérigo"),
    DRUIDA("Druida"),
    FEITICEIRO("Feiticeiro"),
    GUARDAO("Guardião"),
    GUERREIRO("Guerreiro"),
    LADINO("Ladino"),
    MAGO("Mago"),
    MONGE("Monge"),
    PALADINO("Paladino");

    private final String nomeClasse;

    NomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }
}
