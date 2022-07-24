package com.yaphet.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

public class Chapa {

    public void test() throws UnirestException {
        Gson gson = new Gson();
        HttpResponse<JsonNode> response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer CHASECK-AAAAAAAAAAAAAAAAAAA")
                .field( "amount", "100")
                .field( "currency", "ETB")
                .field("first_name", "Abebe")
                .field("last_name", "Bikila")
                .field(  "email", "abebe@bikila.com")
                .field( "tx_ref", "tx-myecommerce12345")
                .asJson();
        Map<String, String> responseBody = gson.fromJson(response.getBody().toString(), Map.class);

    }
}
