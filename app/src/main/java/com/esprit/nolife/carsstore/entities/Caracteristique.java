package com.esprit.nolife.carsstore.entities;

/**
 * Created by Souhaib on 14/11/2016.
 */

public class Caracteristique {
    private String caracteristiqueId;
    private String carroserie;
    private String nombrePlace;
    private String nombrePorte;
    private String longueur;
    private String largeur;
    private String hauteur;
    private String volumeCoffre;


    public String getCaracteristiqueId() {
        return caracteristiqueId;
    }

    public void setCaracteristiqueId(String caracteristiqueId) {
        this.caracteristiqueId = caracteristiqueId;
    }

    public String getCarroserie() {
        return carroserie;
    }

    public void setCarroserie(String carroserie) {
        this.carroserie = carroserie;
    }

    public String getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(String nombrePlace) {
        this.nombrePlace = nombrePlace;
    }

    public String getNombrePorte() {
        return nombrePorte;
    }

    public void setNombrePorte(String nombrePorte) {
        this.nombrePorte = nombrePorte;
    }

    public String getLongueur() {
        return longueur;
    }

    public void setLongueur(String longueur) {
        this.longueur = longueur;
    }

    public String getLargeur() {
        return largeur;
    }

    public void setLargeur(String largeur) {
        this.largeur = largeur;
    }

    public String getHauteur() {
        return hauteur;
    }

    public void setHauteur(String hauteur) {
        this.hauteur = hauteur;
    }

    public String getVolumeCoffre() {
        return volumeCoffre;
    }

    public void setVolumeCoffre(String volumeCoffre) {
        this.volumeCoffre = volumeCoffre;
    }
}
