package com.yaphet.chapa.model;

import com.google.gson.annotations.SerializedName;

/**
 * The ResponseData class is an object representation of JSON data
 * received from Chapa API.
 */
public class ResponseData {

    private String message;
    private String status;
    private int statusCode;
    private Data data;

    public ResponseData() {

    }

    public ResponseData(String message, String status, int statusCode, Data data) {
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public ResponseData setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ResponseData setStatus(String status) {
        this.status = status;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public ResponseData setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Data getData() {
        return data;
    }

    public ResponseData setData(Data data) {
        this.data = data;
        return this;
    }

    static class Data {

        @SerializedName("checkout_url")
        private String checkOutUrl;

        public String getCheckOutUrl() {
            return checkOutUrl;
        }

        public Data setCheckOutUrl(String checkOutUrl) {
            this.checkOutUrl = checkOutUrl;
            return this;
        }
    }
}
