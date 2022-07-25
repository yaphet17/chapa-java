package com.yaphet.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ApiFields{

    @NotNull(message = "amount field required")
    @DecimalMin(value = "1.00", message = "amount can't be less than 1")
    private BigDecimal amount;
    @NotNull(message = "currency field required")
    private String currency;
    @NotNull(message = "first_name field required")
    private String first_name;
    @NotNull(message = "last_name field required")
    private String last_name;
    @Email(message = "Invalid email address")
    @NotNull(message = "email field required")
    private String email;
    @NotNull(message = "tx_ref field required")
    private String tx_ref;
    private String callback_url;
    private String customization_title;
    private String customization_description;
}
