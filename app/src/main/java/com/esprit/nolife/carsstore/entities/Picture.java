package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Souhaib on 12/11/2016.
 */

public class Picture implements Serializable {


    private String id;
    private String url;
    private String modelId;

    public Picture() {

    }

    public Picture(String url) {
        this.url = url;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getModelId() {
        return modelId;
    }
}
