package com.yaphet.chapa;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The PostData class is an object representation of JSON form data
 * that will be posted to Chapa API.
 */
@Getter
@Setter
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
    private String first_name;
    @NotNull(message = "The last_name field is required")
    private String last_name;
    @NotNull(message = "The tx_ref field is required")
    private String tx_ref;
    private String callback_url;
    private Map<String, String> customizations;
}
