package com.yaphet.chapa;

import java.util.HashMap;
import java.util.Map;

import com.yaphet.chapa.client.ChapaClient;
import com.yaphet.chapa.client.ChapaClientImpl;

/**
 * The Chapa class is responsible for making GET and POST request to Chapa API
 * to initialize payment and verify transactions.
 */
public class Chapa {

    private ChapaClient chapaClient;
    private static String responseBody;
    private final String BASE_URL = "https://api.chapa.co/v1";
    private final String SECRETE_KEY;

    /**
     *
     * @param secreteKey    A secrete key provided from Chapa.
     */
    public Chapa(String secreteKey){
        this.SECRETE_KEY = secreteKey;
        this.chapaClient = new ChapaClientImpl();
    }

    /**
     *
     * @param secreteKey    A secrete key provided from Chapa.
     * @param chapaClient   Implementation of {@link com.yaphet.chapa.client.ChapaClient}
     *                      which is used to make API calls to Chapa.
     */
    public Chapa(ChapaClient chapaClient, String secreteKey){
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
    }


    /**
     *
     * @param postData  Object of {@link com.yaphet.chapa.PostData} instantiated with
     *                  post fields.
     * @return          The current invoking object.
     */
    public Chapa initialize(PostData postData) throws Throwable{
        Util.validatePostData(postData);

        Map<String, String> fields = new HashMap<>();
        fields.put( "amount", postData.getAmount().toString());
        fields.put("currency", postData.getCurrency());
        fields.put("email", postData.getEmail());
        fields.put("first_name", postData.getFirst_name());
        fields.put("last_name", postData.getLast_name());
        fields.put("tx_ref", postData.getTx_ref());
        fields.put("customization[title]",  postData.getCustomization_title());
        fields.put("customization[description]", postData.getCustomization_description());

        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", fields, null, SECRETE_KEY);
        return this;
    }

    /**
     *
     * @param jsonData  JSON data which contains post fields.
     * @return          The current invoking object.
     */

    public Chapa initialize(String jsonData) throws Throwable {
        Util.validatePostData(jsonData);
        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", null, jsonData, SECRETE_KEY);
        return this;
    }

    /**
     *
     * @param transactionRef   Unique transaction reference which was associated
     *                         with tx_ref field in post data.
     * @return                 The current invoking object.
     */
    public Chapa verify(String transactionRef) throws  Throwable{
        responseBody = chapaClient.get(BASE_URL + "/transaction/verify/" + transactionRef, SECRETE_KEY);
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
     * @return  Map representation of the response JSON data.
     */
    public Map<String, String> asMap(){
        return Util.jsonToMap(responseBody);
    }

}
