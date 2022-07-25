package com.yaphet;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yaphet.chapa.ApiFields;
import com.yaphet.chapa.Chapa;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigDecimal;
import java.util.Date;

public class App
{
    public static void main( String[] args ) throws UnirestException {
        Dotenv dotenv = Dotenv.load();
        String secreteKey = dotenv.get("SECRETE_KEY");

        String transactionRef= "transaction-" + new Date();

        ApiFields fields = ApiFields.builder()
                .amount(new BigDecimal("100"))
                .currency( "ETB")
                .first_name("Yafet")
                .last_name("Berhanu")
                .email("yafetberhanu3@gmail.com")
                .tx_ref(transactionRef)
                .callback_url("https://chapa.co")
                .customization_title("I love e-commerce")
                .customization_description("It is time to pay")
                .customization_logo("My logo")
                .build();

        Chapa chapa = new Chapa(secreteKey);
        System.out.println(chapa.initialize(fields).asString());
//        System.out.println(chapa.verify(transactionRef).asMap());
    }
}
