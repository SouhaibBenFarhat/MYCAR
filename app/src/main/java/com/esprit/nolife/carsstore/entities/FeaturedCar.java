package com.esprit.nolife.carsstore.entities;

import java.io.Serializable;

/**
 * Created by Souhaib on 07/11/2016.
 */

public class FeaturedCar implements Serializable {

    public String featuredCarId;
    public String title;
    public String link;
    public String description;
    public String author;
    public String category;
    public String pubDate;
    public String thumbnailUrl;
    public String enclosure;

    public String getFeaturedCarId() {
        return featuredCarId;
    }

    public void setFeaturedCarId(String featuredCarId) {
        this.featuredCarId = featuredCarId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }
}
