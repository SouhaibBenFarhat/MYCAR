package com.esprit.nolife.carsstore.entities;

/**
 * Created by Firas on 10/12/2016.
 */

public class AnnonceOptionExterieur {
    private int id;
    private OptionExterieur optionExterieur;
    private Annonce annonce;

    public AnnonceOptionExterieur() {
    }

    public AnnonceOptionExterieur(int id, OptionExterieur optionExterieur, Annonce annonce) {
        this.id = id;
        this.optionExterieur = optionExterieur;
        this.annonce = annonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public OptionExterieur getOptionExterieur() {
        return optionExterieur;
    }

    public void setOptionExterieur(OptionExterieur optionExterieur) {
        this.optionExterieur = optionExterieur;
    }
}
