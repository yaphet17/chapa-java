package com.yaphet.chapa.model;

import java.time.LocalDateTime;

import lombok.Setter;

@Setter
public class Bank {

    private String id;
    private String name;
    private String country_id;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public LocalDateTime getCreated_at() {
        return LocalDateTime.parse(created_at.substring(0, created_at.lastIndexOf(".")));
    }

    public LocalDateTime getUpdated_at() {
        return LocalDateTime.parse(updated_at.substring(0, updated_at.lastIndexOf(".")));
    }
}
