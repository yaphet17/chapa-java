package com.yaphet;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yaphet.api.ApiFields;
import com.yaphet.api.Chapa;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigDecimal;
import java.util.Date;

public class App
{
    public static void main( String[] args ) throws UnirestException {
        Dotenv dotenv = Dotenv.load();
        String secreteKey = dotenv.get("SECRETE_KEY");

        ApiFields fields = ApiFields.builder()
                .amount(new BigDecimal("100"))
                .currency( "ETB")
                .first_name("Yafet")
                .last_name("Berhanu")
                .email("yafetberhanu3@gmail.com")
                .tx_ref("transaction-" + new Date())
                .customization_title("I love e-commerce")
                .customization_description("It is time to pay")
                .build();
        Chapa chapa = new Chapa(secreteKey);
        System.out.println(chapa.initialize(fields));
    }
}
