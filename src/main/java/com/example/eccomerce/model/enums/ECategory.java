package com.example.eccomerce.model.enums;

public enum ECategory {
    AGUA("Agua"),
    GASEOSA("Gaseosa"),
    JUGO("Jugo"),
    ENERGIZANTE("Energizante"),
    BEBIDA_ISOTONICA("Bebida isotónica"),
    CERVEZA("Cerveza"),
    VINO("Vino"),
    VERMUT("Vermut"),
    FERNET("Fernet"),
    WHISKY("Whisky"),
    VODKA("Vodka"),
    RON("Ron"),
    GIN("Gin"),
    TEQUILA("Tequila"),
    LICOR("Licor"),
    SIDRA("Sidra"),
    CHAMPAGNE("Champagne"),
    APERITIVO("Aperitivo"),
    SIN_ALCOHOL("Sin alcohol"),
    OTRO("Otro");

    private final String name;

    ECategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
