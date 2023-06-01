package com.yaphet.chapa.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * The PostData class is an object representation of JSON form data
 * that will be posted to Chapa API.
 */
public class PostData {

    private BigDecimal amount;
    private String currency;
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("tx_ref")
    private String txRef;
    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("return_url")
    private String returnUrl;
    @SerializedName("subaccounts[id]")
    private String subAccountId;
    private Customization customization;

    public BigDecimal getAmount() {
        return amount;
    }

    public PostData setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public PostData setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PostData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PostData setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PostData setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getTxRef() {
        return txRef;
    }

    public PostData setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public PostData setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
        return this;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public PostData setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
        return this;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public PostData setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
        return this;
    }

    public Customization getCustomization() {
        return customization;
    }

    public PostData setCustomization(Customization customization) {
        this.customization = customization;
        return this;
    }
}
