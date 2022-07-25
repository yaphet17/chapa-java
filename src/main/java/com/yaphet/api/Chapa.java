package com.yaphet.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Chapa {

    private static HttpResponse<JsonNode> response;
    private final String SECRETE_KEY;

    public Chapa(String secreteKey){
        this.SECRETE_KEY = secreteKey;
    }


    public String initialize(ApiFields apiFields) throws UnirestException {
        FieldValidator.validateFields(apiFields);

        response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer " + SECRETE_KEY)
                .field( "amount", apiFields.getAmount().toString())
                .field( "currency", apiFields.getCurrency())
                .field("first_name", apiFields.getFirst_name())
                .field("last_name", apiFields.getLast_name())
                .field("email", apiFields.getEmail())
                .field("tx_ref", apiFields.getTx_ref())
                .field("customization[title]",  apiFields.getCustomization_title())
                .field("customization[description]", apiFields.getCustomization_description())
                .asJson();

        return response.getBody().toString();
    }

    public String initialize(String jsonData) throws UnirestException {
        FieldValidator.validateFields(jsonData);

        response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer " + SECRETE_KEY)
                .body(jsonData)
                .asJson();
        return response.getBody().toString();
    }

}
