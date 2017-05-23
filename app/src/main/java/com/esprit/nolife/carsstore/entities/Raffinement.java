package com.esprit.nolife.carsstore.entities;

/**
 * Created by Souhaib on 14/11/2016.
 */

public class Raffinement {
    private String raffinementId;
    private String connectivite;
    private String nombreAirbag;
    private String climatisation;
    private String jante;

    public String getRaffinementId() {
        return raffinementId;
    }

    public void setRaffinementId(String raffinementId) {
        this.raffinementId = raffinementId;
    }

    public String getConnectivite() {
        return connectivite;
    }

    public void setConnectivite(String connectivite) {
        this.connectivite = connectivite;
    }

    public String getNombreAirbag() {
        return nombreAirbag;
    }

    public void setNombreAirbag(String nombreAirbag) {
        this.nombreAirbag = nombreAirbag;
    }

    public String getClimatisation() {
        return climatisation;
    }

    public void setClimatisation(String climatisation) {
        this.climatisation = climatisation;
    }

    public String getJante() {
        return jante;
    }

    public void setJante(String jante) {
        this.jante = jante;
    }
}
