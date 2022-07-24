package com.yaphet;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yaphet.api.ApiFields;
import com.yaphet.api.Chapa;

import java.math.BigDecimal;
import java.util.Date;

public class App
{
    public static void main( String[] args ) throws UnirestException {
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

        Chapa chapa = new Chapa("CHASECK_TEST-T651GbaeRkJPdOptqObbHAYCa1O2BgHX", fields);
        System.out.println(chapa.initialize());
    }
}
