package com.yaphet.chapa.model;

import com.google.gson.annotations.SerializedName;

public class Bank {

    private String id;
    private String name;
    @SerializedName("country_id")
    private int countryId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public Bank setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bank setName(String name) {
        this.name = name;
        return this;
    }

    public int getCountryId() {
        return countryId;
    }

    public Bank setCountryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Bank setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Bank setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
