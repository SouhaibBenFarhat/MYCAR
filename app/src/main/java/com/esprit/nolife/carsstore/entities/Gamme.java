package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Souhaib on 12/11/2016.
 */

public class Gamme implements Serializable {
    private String id;
    private String gamme;
    private String prix;
    private String modelId;
    private String caracteristiqueId;
    private String raffinementId;
    private String motorisationId;
    private String bodyCarId;
    private String pictureUrl;
    private String description;
    private String brandId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGamme() {
        return gamme;
    }

    public void setGamme(String gamme) {
        this.gamme = gamme;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCaracteristiqueId() {
        return caracteristiqueId;
    }

    public void setCaracteristiqueId(String caracteristiqueId) {
        this.caracteristiqueId = caracteristiqueId;
    }

    public String getRaffinementId() {
        return raffinementId;
    }

    public void setRaffinementId(String raffinementId) {
        this.raffinementId = raffinementId;
    }

    public String getBodyCarId() {
        return bodyCarId;
    }

    public void setBodyCarId(String bodyCarId) {
        this.bodyCarId = bodyCarId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getMotorisationId() {
        return motorisationId;
    }

    public void setMotorisationId(String motorisationId) {
        this.motorisationId = motorisationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}
