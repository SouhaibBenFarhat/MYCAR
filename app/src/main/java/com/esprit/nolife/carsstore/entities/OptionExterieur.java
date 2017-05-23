package com.esprit.nolife.carsstore.entities;

/**
 * Created by Firas on 10/12/2016.
 */

public class OptionExterieur {
    private int id;
    private String optionExterieur;

    public OptionExterieur(int id, String optionExterieur) {
        this.id = id;
        this.optionExterieur = optionExterieur;
    }

    public OptionExterieur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionExterieur() {
        return optionExterieur;
    }

    public void setOptionExterieur(String optionExterieur) {
        this.optionExterieur = optionExterieur;
    }
}
