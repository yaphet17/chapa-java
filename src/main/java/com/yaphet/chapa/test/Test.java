package com.yaphet.chapa.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.yaphet.chapa.Chapa;
import com.yaphet.chapa.PostData;

public class Test {

    public static void main(String[] args) throws Throwable {
        Chapa chapa = new Chapa("CHASECK_TEST-LgjzxoU0DE8L9SnPsK4c1wWqFzhlrJVH");
        Map<String, String> customizations = new HashMap<>();
        customizations.put("customization[title]", "E-commerce");
        customizations.put("customization[description]", "It is time to pay");
        customizations.put("customization[logo]", "https://mylogo.com/log.png");
        PostData formData = PostData.builder()
                .amount(new BigDecimal("100"))
                .currency( "ETB")
                .first_name("Abebe")
                .last_name("Bikila")
                .email("abebe@bikila.com")
                .tx_ref("tx-myecommerce12aaa345")
                .callback_url("https://chapa.co")
                .customizations(customizations)
                .build();

//        System.out.println(chapa.initialize(formData).asString());
        System.out.println(chapa.verify("tx-myecommerce12aaa345").asMap());
    }

}
