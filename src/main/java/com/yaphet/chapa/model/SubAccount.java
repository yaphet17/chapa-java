package com.yaphet.chapa.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubAccount {

    @NotNull(message = "Business name is required")
    @SerializedName("business_name")
    private String businessName;
    @NotNull(message = "Bank code is required")
    @SerializedName("bank_code")
    private String bankCode;
    @NotNull(message = "Account name is required")
    @SerializedName("account_name")
    private String accountName;
    @NotNull(message = "Account number is required")
    @SerializedName("account_number")
    private String accountNumber;
    @NotNull(message = "Split type is required")
    @SerializedName("split_type")
    private SplitType splitType;
    @NotNull(message = "Split type is required")
    @DecimalMin(value = "0.01", message = "Invalid amount")
    @SerializedName("split_value")
    private Double splitValue;

}
