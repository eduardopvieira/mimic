package br.edu.ufersa.mimic.model.enums;

public enum CategoriaTalento {
    ORIGEM("Origem"),
    GERAL("Geral"),
    ESTILO_DE_LUTA("Estilo de Luta"),
    DADIVA_EPICA("Dádiva Épica");

    private final String categoriaTalento;

    CategoriaTalento(String categoriaTalento) {
        this.categoriaTalento = categoriaTalento;
    }

    public String getCategoriaTalento() {
        return categoriaTalento;
    }
}
