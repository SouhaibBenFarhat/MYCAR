package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Firas on 10/12/2016.
 */

public class Annonce implements Serializable {
        private String id;
        private String type;
        private String brand;
        private String model;
        private String annee;
        private String transmission;
        private String puissance;
        private String kilometrage;
        private String color;
        private String nobrePortes;
        private String carburant;
        private String num_tel;
        private String etat;
        private String prix;
        private String photo;
        private String user_id;

    public Annonce() {
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", annee='" + annee + '\'' +
                ", transmission='" + transmission + '\'' +
                ", puissance='" + puissance + '\'' +
                ", kilometrage='" + kilometrage + '\'' +
                ", color='" + color + '\'' +
                ", nobrePortes='" + nobrePortes + '\'' +
                ", carburant='" + carburant + '\'' +
                ", num_tel='" + num_tel + '\'' +
                ", etat='" + etat + '\'' +
                ", prix='" + prix + '\'' +
                ", photo='" + photo + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }

    public Annonce(String id, String type, String brand, String model, String annee, String transmission, String puissance, String kilometrage, String color, String nobrePortes, String carburant, String num_tel, String etat, String prix, String photo, String user_id) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.annee = annee;
        this.transmission = transmission;
        this.puissance = puissance;
        this.kilometrage = kilometrage;
        this.color = color;
        this.nobrePortes = nobrePortes;
        this.carburant = carburant;
        this.num_tel = num_tel;
        this.etat = etat;
        this.prix = prix;
        this.photo = photo;
        this.user_id = user_id;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public String getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(String kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNobrePortes() {
        return nobrePortes;
    }

    public void setNobrePortes(String nobrePortes) {
        this.nobrePortes = nobrePortes;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
