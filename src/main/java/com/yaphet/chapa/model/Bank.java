package com.yaphet.chapa.model;

import java.time.LocalDateTime;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Bank {

    private String id;
    private String name;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryId() {
        return countryId;
    }

    public LocalDateTime getCreatedAt() {
        return LocalDateTime.parse(createdAt.substring(0, createdAt.lastIndexOf(".")));
    }

    public LocalDateTime getUpdatedAt() {
        return LocalDateTime.parse(updatedAt.substring(0, updatedAt.lastIndexOf(".")));
    }
}
