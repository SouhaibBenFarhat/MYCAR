package com.esprit.nolife.carsstore.entities;

/**
 * Created by Firas on 10/12/2016.
 */

public class OptionInterieur {
    private int id;
    private String optionInterieur;

    public OptionInterieur(int id, String optionInterieur) {
        this.id = id;
        this.optionInterieur = optionInterieur;
    }

    public OptionInterieur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionInterieur() {
        return optionInterieur;
    }

    public void setOptionInterieur(String optionInterieur) {
        this.optionInterieur = optionInterieur;
    }
}
