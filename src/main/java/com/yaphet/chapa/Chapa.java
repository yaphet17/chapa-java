package com.yaphet.chapa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yaphet.chapa.client.ChapaClient;
import com.yaphet.chapa.client.ChapaClientImpl;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.SubAccount;

/**
 * The Chapa class is responsible for making GET and POST request to Chapa API
 * to initialize payment and verify transactions.
 */
public class Chapa {

    private static String responseBody;
    private final ChapaClient chapaClient;
    private final String VERSION = "v1";
    private final String BASE_URL = "https://api.chapa.co/" + VERSION;
    private final String SECRETE_KEY;

    /**
     * @param secreteKey A secrete key provided from Chapa.
     */
    public Chapa(String secreteKey) { // TODO: consider deprecating this since it makes it hard to test this class
        this.SECRETE_KEY = secreteKey;
        this.chapaClient = new ChapaClientImpl();
    }

    /**
     * @param secreteKey  A secrete key provided from Chapa.
     * @param chapaClient Implementation of {@link com.yaphet.chapa.client.ChapaClient}
     *                    which is used to make API calls to Chapa.
     */
    public Chapa(ChapaClient chapaClient, String secreteKey) {
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
    }


    /**
     * @param postData Object of {@link PostData} instantiated with
     *                 post fields.
     * @return         The current invoking object.
     */
    public Chapa initialize(PostData postData) throws Throwable { // TODO: consider creating custom exception handler and wrap any exception thrown by http client
        Util.validatePostData(postData);

        Map<String, Object> fields = new HashMap<>();
        fields.put("amount", postData.getAmount().toString());
        fields.put("currency", postData.getCurrency());
        fields.put("email", postData.getEmail());
        fields.put("first_name", postData.getFirstName());
        fields.put("last_name", postData.getLastName());
        fields.put("tx_ref", postData.getTxRef());

        Map<String, String> customizations = postData.getCustomizations();
        String callbackUrl = postData.getCallbackUrl();

        if (callbackUrl != null && !callbackUrl.isEmpty()) {
            fields.put("callback_url", callbackUrl);
        }

        if (customizations != null && !customizations.isEmpty()) {
            // TODO: consider directly adding all values to fields map
            if (customizations.containsKey("customization[title]") && Util.notNullAndEmpty(customizations.get("customization[title]"))) {
                fields.put("customization[title]", customizations.get("customization[title]"));
            }

            if (customizations.containsKey("customization[description]") && Util.notNullAndEmpty(customizations.get("customization[description]"))) {
                fields.put("customization[description]", customizations.get("customization[description]"));
            }

            if (customizations.containsKey("customization[logo]") && Util.notNullAndEmpty(customizations.get("customization[logo]"))) {
                fields.put("customization[logo]", customizations.get("customization[logo]"));
            }
        }

        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", fields, SECRETE_KEY);
        return this;
    }

    /**
     * @param jsonData JSON data which contains post fields.
     * @return         The current invoking object.
     */

    public Chapa initialize(String jsonData) throws Throwable {
        Util.validatePostData(jsonData);
        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", jsonData, SECRETE_KEY);
        return this;
    }

    /**
     * @param transactionRef Unique transaction reference which was associated
     *                       with tx_ref field in post data.
     * @return               The current invoking object.
     * @throws Throwable
     */
    public Chapa verify(String transactionRef) throws Throwable {
        if(!Util.notNullAndEmpty(transactionRef)){
            throw new IllegalArgumentException("Transaction reference can't be null or empty");
        }
        responseBody = chapaClient.get(BASE_URL + "/transaction/verify/" + transactionRef, SECRETE_KEY);
        return this;
    }

    /**
     * @return          List of banks supported by Chapa
     * @throws Throwable
     */
    public List<Bank> banks() throws Throwable {
        responseBody = chapaClient.get(BASE_URL + "/banks", SECRETE_KEY);
        return  Util.extractBanks(responseBody);
    }

    /**
     *
     * @param subAccount
     * @return
     * @throws Throwable
     */
    public Chapa createSubAccount(SubAccount subAccount) throws Throwable {
        Util.vali
        responseBody = chapaClient.get(BASE_URL + "/subaccount", SECRETE_KEY);
        return this;
    }

    /**
     * @return          String representation of the response JSON data.
     */
    public String asString() {
        return responseBody;
    }

    /**
     * @return          Map<String, String> representation of the response JSON data.
     */
    public Map<String, String> asMap() {
        return Util.jsonToMap(responseBody);
    }

}
