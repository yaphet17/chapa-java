package com.yaphet.chapa.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.yaphet.chapa.utility.LocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VerifyResponseData extends ResponseData {

    private Data data;

    public VerifyResponseData() {
    }

    public VerifyResponseData(String rawJson, String message, String status, int statusCode, Data data) {
        super(rawJson, message, status, statusCode);
        this.data = data;
    }

    public VerifyResponseData setMessage(String message) {
        return (VerifyResponseData) super.setMessage(message);
    }

    public VerifyResponseData setStatus(String status) {
        return (VerifyResponseData) super.setStatus(status);
    }

    public VerifyResponseData setStatusCode(int statusCode) {
        return (VerifyResponseData) super.setStatusCode(statusCode);
    }

    public VerifyResponseData setRawJson(String rawJson) {
        return (VerifyResponseData) super.setRawJson(rawJson);
    }

    public VerifyResponseData setData(Data data) {
        this.data = data;
        return this;
    }

    public Data getData() {
        return data;
    }

  public static class Data {

      @SerializedName("first_name")
      private String firstName;
      @SerializedName("last_name")
      private String lastName;
      private String email;
      private String currency;
      private BigDecimal amount;
      private BigDecimal charge;
      private String mode;
      private String method;
      private String type;
      private String status;
      private String reference;
      @SerializedName("tx_ref")
      private String txRef;
      private Customization customization;
      private String meta;
      @SerializedName("created_at")
      @JsonAdapter(LocalDateTimeDeserializer.class)
      private LocalDateTime createdAt;
      @SerializedName("updated_at")
      @JsonAdapter(LocalDateTimeDeserializer.class)
      private LocalDateTime updatedAt;

      @SerializedName("first_name")
      public String getFirstName() {
          return firstName;
      }

      public VerifyResponseData.Data setFirstName(String firstName) {
          this.firstName = firstName;
          return this;
      }

      public String getLastName() {
          return lastName;
      }

      public VerifyResponseData.Data setLastName(String lastName) {
          this.lastName = lastName;
          return this;
      }

      public String getEmail() {
          return email;
      }

      public VerifyResponseData.Data setEmail(String email) {
          this.email = email;
          return this;
      }

      public String getCurrency() {
          return currency;
      }

      public VerifyResponseData.Data setCurrency(String currency) {
          this.currency = currency;
          return this;
      }

      public BigDecimal getAmount() {
          return amount;
      }

      public VerifyResponseData.Data setAmount(BigDecimal amount) {
          this.amount = amount;
          return this;
      }

      public BigDecimal getCharge() {
          return charge;
      }

      public VerifyResponseData.Data setCharge(BigDecimal charge) {
          this.charge = charge;
          return this;
      }

      public String getMode() {
          return mode;
      }

      public VerifyResponseData.Data setMode(String mode) {
          this.mode = mode;
          return this;
      }

      public String getMethod() {
          return method;
      }

      public VerifyResponseData.Data setMethod(String method) {
          this.method = method;
          return this;
      }

      public String getType() {
          return type;
      }

      public VerifyResponseData.Data setType(String type) {
          this.type = type;
          return this;
      }

      public String getStatus() {
          return status;
      }

      public VerifyResponseData.Data setStatus(String status) {
          this.status = status;
          return this;
      }

      public String getReference() {
          return reference;
      }

      public VerifyResponseData.Data setReference(String reference) {
          this.reference = reference;
          return this;
      }

      public String getTxRef() {
          return txRef;
      }

      public VerifyResponseData.Data setTxRef(String txRef) {
          this.txRef = txRef;
          return this;
      }

      public Customization getCustomization() {
          return customization;
      }

      public VerifyResponseData.Data setCustomization(Customization customization) {
          this.customization = customization;
          return this;
      }

      public String getMeta() {
          return meta;
      }

      public VerifyResponseData.Data setMeta(String meta) {
          this.meta = meta;
          return this;
      }

      public LocalDateTime getCreatedAt() {
          return createdAt;
      }

      public VerifyResponseData.Data setCreatedAt(LocalDateTime createdAt) {
          this.createdAt = createdAt;
          return this;
      }

      public LocalDateTime getUpdatedAt() {
          return updatedAt;
      }

      public VerifyResponseData.Data setUpdatedAt(LocalDateTime updatedAt) {
          this.updatedAt = updatedAt;
          return this;
      }
  }
}
