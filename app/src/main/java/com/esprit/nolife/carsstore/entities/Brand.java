package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Souhaib on 06/11/2016.
 */

public class Brand implements Serializable {
    public String brandId;
    public String brand;
    public String logo;
    public String cover;


    public Brand() {

    }


    public Brand(String brandId, String brand, String logo) {
        this.brandId = brandId;
        this.brand = brand;
        this.logo = logo;
    }

    public Brand(String brand, String logo) {
        this.brand = brand;
        this.logo = logo;
    }

    public Brand(String brand) {
        this.brand = brand;

    }

    @Override
    public String toString() {
        return "Brand{" +
                "brandId='" + brandId + '\'' +
                ", brand='" + brand + '\'' +
                ", logo='" + logo + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
