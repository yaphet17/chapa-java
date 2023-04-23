package com.yaphet.chapa.model;

import com.google.gson.annotations.SerializedName;

public class InitializeResponseData extends ResponseData {

     private Data data;

     public InitializeResponseData() {
     }

     public InitializeResponseData(String rawJson, String message, String status, int statusCode, Data data) {
          super(rawJson, message, status, statusCode);
          this.data = data;
     }

     public InitializeResponseData setMessage(String message) {
          return (InitializeResponseData) super.setMessage(message);
     }

     public InitializeResponseData setStatus(String status) {
          return (InitializeResponseData) super.setStatus(status);
     }

     public InitializeResponseData setStatusCode(int statusCode) {
          return (InitializeResponseData) super.setStatusCode(statusCode);
     }

     public InitializeResponseData setRawJson(String rawJson) {
          return (InitializeResponseData) super.setRawJson(rawJson);
     }

     public InitializeResponseData setData(Data data) {
          this.data = data;
          return this;
     }

     public Data getData() {
          return data;
     }

    public static class Data {
         @SerializedName("checkout_url")
         private String checkOutUrl;

         public String getCheckOutUrl() {
              return checkOutUrl;
         }

         public InitializeResponseData.Data setCheckOutUrl(String checkOutUrl) {
              this.checkOutUrl = checkOutUrl;
              return this;
         }
    }
}
