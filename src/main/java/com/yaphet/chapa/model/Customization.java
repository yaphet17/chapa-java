package com.yaphet.chapa.model;

public class Customization {

    private String title;
    private String description;
    private String logo;

    public String getTitle() {
        return title;
    }

    public Customization setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Customization setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public Customization setLogo(String logo) {
        this.logo = logo;
        return this;
    }
}
