package com.esprit.nolife.carsstore.entities;

/**
 * Created by Souhaib on 07/11/2016.
 */

public class BodyType {

    public String id;
    public String body;
    public String logo;


    public BodyType(String body, String logo) {
        this.body = body;
        this.logo = logo;
    }

    public BodyType() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
