package com.yaphet.chapa.model;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

/**
 * The PostData class is an object representation of JSON form data
 * that will be posted to Chapa API.
 */

@Getter
@Builder
public class PostData {

    @NotNull(message = "The amount field is required")
    @DecimalMin(value = "0.01", message = "Invalid amount")
    private BigDecimal amount;
    @NotNull(message = "The currency field is required")
    private String currency;
    @Email(message = "Invalid email address")
    @NotNull(message = "The email field is required")
    private String email;
    @NotNull(message = "The first_name is field required")
    @SerializedName("first_name")
    private String firstName;
    @NotNull(message = "The last_name field is required")
    @SerializedName("last_name")
    private String lastName;
    @NotNull(message = "The tx_ref field is required")
    @SerializedName("tx_ref")
    private String txRef;
    @SerializedName("callback_url")
    private String callbackUrl;
    @SerializedName("subaccounts[id]")
    private String subAccountId;
    private Map<String, String> customizations;
}
