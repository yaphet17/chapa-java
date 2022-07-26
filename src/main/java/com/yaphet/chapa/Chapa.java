package com.yaphet.chapa;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHeaders;

import java.util.Map;

public class Chapa {

    private static HttpResponse<JsonNode> response;
    private static final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private static final String acceptEncodingHeader = HttpHeaders.ACCEPT_ENCODING;
    private static String responseBody;
    private final String BASE_URL = "https://api.chapa.co/v1/transaction";
    private final String SECRETE_KEY;

    public Chapa(String secreteKey){
        this.SECRETE_KEY = secreteKey;
    }


    public Chapa initialize(PostData postData) throws UnirestException {
        Util.validatePostData(postData);

        response = Unirest.post(BASE_URL + "/initialize")
                .header(acceptEncodingHeader,"application/json")
                .header(authorizationHeader, "Bearer " + SECRETE_KEY)
                .field( "amount", postData.getAmount().toString())
                .field( "currency", postData.getCurrency())
                .field("email", postData.getEmail())
                .field("first_name", postData.getFirst_name())
                .field("last_name", postData.getLast_name())
                .field("tx_ref", postData.getTx_ref())
                .field("customization[title]",  postData.getCustomization_title())
                .field("customization[description]", postData.getCustomization_description())
                .asJson();

        responseBody = response.getBody().toString();
        return this;
    }

    public Chapa initialize(String jsonData) throws UnirestException {
        Util.validatePostData(jsonData);

        response = Unirest.post(BASE_URL + "/initialize")
                .header(acceptEncodingHeader,"application/json")
                .header(authorizationHeader, "Bearer " + SECRETE_KEY)
                .body(jsonData)
                .asJson();
        responseBody = response.getBody().toString();
        return this;
    }

    public Chapa verify(String transactionRef) throws UnirestException {
        response = Unirest.get(BASE_URL + "/verify/" + transactionRef)
                .header(acceptEncodingHeader,"application/json")
                .header(authorizationHeader, "Bearer " + SECRETE_KEY)
                .asJson();
        responseBody = response.getBody().toString();
        return this;
    }

    public String asString(){
        return responseBody;
    }

    public Map<String, String> asMap(){
        return Util.jsonToMap(responseBody);
    }

}
