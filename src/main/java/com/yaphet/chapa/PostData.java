package com.yaphet.chapa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * The PostData class is an object representation of json form data
 * that will be posted to Chapa API
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
    private String customization_title;
    private String customization_description;
    private String customization_logo;
}
