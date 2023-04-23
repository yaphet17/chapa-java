package com.yaphet.chapa.model;

import com.google.gson.annotations.SerializedName;

public class SubAccount {

    @SerializedName("business_name")
    private String businessName;
    @SerializedName("bank_code")
    private String bankCode;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account_number")
    private String accountNumber;
    @SerializedName("split_type")
    private SplitType splitType;
    @SerializedName("split_value")
    private Double splitValue;

    public String getBusinessName() {
        return businessName;
    }

    public SubAccount setBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public String getBankCode() {
        return bankCode;
    }

    public SubAccount setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public SubAccount setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public SubAccount setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public SubAccount setSplitType(SplitType splitType) {
        this.splitType = splitType;
        return this;
    }

    public Double getSplitValue() {
        return splitValue;
    }

    public SubAccount setSplitValue(Double splitValue) {
        this.splitValue = splitValue;
        return this;
    }
}
