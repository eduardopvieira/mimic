package br.edu.ufersa.mimic.model.enums.equipamento.armadura;

public enum CategoriaArmadura {
    LEVE("Leve"),
    MEDIA("Média"),
    PESADA("Pesada"),
    ESCUDO("Escudo");

    private final String nomeCategoriaArmadura;

    CategoriaArmadura(String nomeCategoriaArmadura) {
        this.nomeCategoriaArmadura = nomeCategoriaArmadura;
    }

    public String getNomeCategoriaArmadura() {
        return nomeCategoriaArmadura;
    }
}
