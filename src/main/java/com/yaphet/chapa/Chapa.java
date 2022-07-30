package com.yaphet.chapa;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHeaders;

import java.util.Map;

/**
 * The Chapa class is responsible for making GET and POST request to Chapa API
 * to initialize payment and verify transactions.
 */
public class Chapa {

    private static HttpResponse<JsonNode> response;
    private static final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private static final String acceptEncodingHeader = HttpHeaders.ACCEPT_ENCODING;
    private static String responseBody;
    private final String BASE_URL = "https://api.chapa.co/v1";
    private final String SECRETE_KEY;

    public Chapa(String secreteKey){
        this.SECRETE_KEY = secreteKey;
    }

    /**
     *
     * @param postData  object of {@link com.yaphet.chapa.PostData} instantiated with
     *                  post fields.
     * @return          the current invoking object.
     */
    public Chapa initialize(PostData postData) {
        Util.validatePostData(postData);

        try {
            response = Unirest.post(BASE_URL + "/transaction/initialize")
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
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        responseBody = response.getBody().toString();
        return this;
    }

    /**
     *
     * @param jsonData  JSON data which contains post fields.
     * @return          the current invoking object.
     */

    public Chapa initialize(String jsonData) throws UnirestException {
        Util.validatePostData(jsonData);

        response = Unirest.post(BASE_URL + "/transaction/initialize")
                .header(acceptEncodingHeader,"application/json")
                .header(authorizationHeader, "Bearer " + SECRETE_KEY)
                .body(jsonData)
                .asJson();
        responseBody = response.getBody().toString();
        return this;
    }

    /**
     *
     * @param transactionRef   unique transaction reference which was associated
     *                         with tx_ref field in post data.
     * @return                 the current invoking object.
     */
    public Chapa verify(String transactionRef) {
        try {
            response = Unirest.get(BASE_URL + "/transaction/verify/" + transactionRef)
                    .header(acceptEncodingHeader,"application/json")
                    .header(authorizationHeader, "Bearer " + SECRETE_KEY)
                    .asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        responseBody = response.getBody().toString();
        return this;
    }

    /**
     *
     * @return  String representation of the response JSON data.
     */
    public String asString(){
        return responseBody;
    }

    /**
     *
     * @return  Map<String, String> representation of the response JSON data.
     */
    public Map<String, String> asMap(){
        return Util.jsonToMap(responseBody);
    }

}
