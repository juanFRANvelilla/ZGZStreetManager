package com.example.zgzstreetmanager.model;


public enum DistrictEnum {
    UNDEFINED("Undefined District"),
    ACTUR_REY_FERNANDO("Junta Municipal Actur-Rey Fernando"),
    CASABLANCA("Junta Municipal Casablanca"),
    CASCO_HISTORICO("Junta Municipal Casco Histórico"),
    CENTRO("Junta Municipal Centro"),
    DELICIAS("Junta Municipal Delicias"),
    EL_RABAL("Junta Municipal El Rabal"),
    LA_ALMOZARA("Junta Municipal La Almozara"),
    LAS_FUENTES("Junta Municipal Las Fuentes"),
    MIRALBUENO("Junta Municipal Miralbueno"),
    OLIVER_VALDEFIERRO("Junta Municipal Oliver-Valdefierro"),
    SAN_JOSE("Junta Municipal San José"),
    SANTA_ISABEL("Junta Municipal Santa Isabel"),
    SUR("Junta Municipal Sur"),
    TORRERO("Junta Municipal Torrero"),
    UNIVERSIDAD("Junta Municipal Universidad");

    private final String displayName;

    DistrictEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
