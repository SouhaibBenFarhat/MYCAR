package com.esprit.nolife.carsstore.entities;

/**
 * Created by Souhaib on 04/11/2016.
 */

public class Voiture {

    private String marque;
    private String modele;

    public Voiture(){

    }

    public Voiture(String marque, String modele) {
        this.marque = marque;
        this.modele = modele;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }
}
