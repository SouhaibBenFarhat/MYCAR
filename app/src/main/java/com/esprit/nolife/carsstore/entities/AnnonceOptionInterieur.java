package com.esprit.nolife.carsstore.entities;

/**
 * Created by Firas on 10/12/2016.
 */

public class AnnonceOptionInterieur {
    private int id;
    private OptionInterieur optionInterieur;
    private Annonce annonce;


    public AnnonceOptionInterieur(int id, Annonce annonce, OptionInterieur optionInterieur) {
        this.id = id;
        this.annonce = annonce;
        this.optionInterieur = optionInterieur;
    }

    public AnnonceOptionInterieur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OptionInterieur getOptionInterieur() {
        return optionInterieur;
    }

    public void setOptionInterieur(OptionInterieur optionInterieur) {
        this.optionInterieur = optionInterieur;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
}
