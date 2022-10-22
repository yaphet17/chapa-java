package com.yaphet.chapa;

import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.SplitType;
import com.yaphet.chapa.model.SubAccount;

class UtilTest {

    @Test
    public void shouldValidatePostData() {
        // given
        PostData formData = PostData.builder()
                .amount(new BigDecimal("100"))
                .currency( "ETB")
                .firstName("Abebe")
                .lastName("Bikila")
                .email("abebe@bikila.com")
                .txRef("tx-myecommerce12345")
                .callbackUrl("https://chapa.co")
//                .customization_title("I love e-commerce")
//                .customization_description("It is time to pay")
//                .customization_logo("My logo")
                .build();

        // then
        assertDoesNotThrow(() -> Util.validatePostData(formData));
    }

    @Test
    public void shouldFailForInvalidPostData(){
        // given
        Map<String, String> customizations = new HashMap<>();
        customizations.put("customization[title]", "E-commerce");
        customizations.put("customization[description]", "It is time to pay");
        customizations.put("customization[logo]", "https://mylogo.com/log.png");
        PostData formData = PostData.builder()
                .currency( "ETB")
                .firstName("Abebe")
                .lastName("Bikila")
                .email("abebe@bikila.com")
                .txRef("tx-myecommerce12345")
                .callbackUrl("https://chapa.co")
                .customizations(customizations)
                .build();

        // then
        assertThrows(ValidationException.class, () -> Util.validatePostData(formData));
    }

    @Test
    void shouldValidatePostDataWithJsonInput() {
        // given
        String formData = " { " +
                "'amount': '100', " +
                "'currency': 'ETB'," +
                "'email': 'abebe@bikila.com'," +
                "'first_name': 'Abebe'," +
                "'last_name': 'Bikila'," +
                "'tx_ref': 'tx-myecommerce12345'," +
                "'callback_url': 'https://chapa.co'," +
                "'customization[title]': 'I love e-commerce'," +
                "'customization[description]': 'It is time to pay'" +
                " }";


        // then
        assertDoesNotThrow(() -> Util.validatePostData(formData));
    }

    @Test
    void shouldFailFoInvalidPostDataWithJsonInput() {
        // given
        String formData = " { " +
                "'amount': '100', " +
                "'email': 'abebe@bikila.com'," +
                "'first_name': 'Abebe'," +
                "'last_name': 'Bikila'," +
                "'tx_ref': 'tx-myecommerce12345'," +
                "'callback_url': 'https://chapa.co'," +
                "'customization[title]': 'I love e-commerce'," +
                "'customization[description]': 'It is time to pay'" +
                " }";

        // then
        assertThrows(ValidationException.class, () -> Util.validatePostData(formData));
    }

}