package com.esprit.nolife.carsstore.entities;

/**
 * Created by Firas on 10/12/2016.
 */

public class AnnoncePhoto {
    private int id;
    private String url;
    private Annonce annonce;

    public AnnoncePhoto() {
    }

    public AnnoncePhoto(int id, String url, Annonce annonce) {
        this.id = id;
        this.url = url;
        this.annonce = annonce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
}
