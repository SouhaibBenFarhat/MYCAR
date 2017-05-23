package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Souhaib on 18/11/2016.
 */

public class Concessionnaire implements Serializable {

    private String concessionnaireId;
    private String name;
    private String numTel;
    private String numFax;
    private String address;
    private String description;
    private String logo;
    private String googleAddress;
    private String webSite;


    public String getConcessionnaireId() {
        return concessionnaireId;
    }

    public void setConcessionnaireId(String concessionnaireId) {
        this.concessionnaireId = concessionnaireId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getNumFax() {
        return numFax;
    }

    public void setNumFax(String numFax) {
        this.numFax = numFax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGoogleAddress() {
        return googleAddress;
    }

    public void setGoogleAddress(String googleAddress) {
        this.googleAddress = googleAddress;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
