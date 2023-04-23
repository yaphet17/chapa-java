package com.yaphet.chapa;

import com.yaphet.chapa.client.ChapaClient;
import com.yaphet.chapa.client.ChapaClientImpl;
import com.yaphet.chapa.model.*;
import com.yaphet.chapa.utility.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Chapa class is responsible for making GET and POST request to Chapa API
 * to initialize payment and verify transactions.
 */
public class Chapa {

    private static String responseBody;
    private static int statusCode;
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
     * @param chapaClient Implementation of {@link ChapaClient}
     *                    which is used to make API calls to Chapa.
     */
    public Chapa(ChapaClient chapaClient, String secreteKey) {
        this.chapaClient = chapaClient;
        this.SECRETE_KEY = secreteKey;
    }


    /**
     * @param postData Object of {@link PostData} instantiated with
     *                 post fields.
     * @return The current invoking object.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public InitializeResponseData initialize(PostData postData) throws Throwable { // TODO: consider creating custom exception handler and wrap any exception thrown by http client
        Map<String, Object> fields = new HashMap<>();
        fields.put("amount", postData.getAmount().toString());
        fields.put("currency", postData.getCurrency());
        fields.put("email", postData.getEmail());
        fields.put("first_name", postData.getFirstName());
        fields.put("last_name", postData.getLastName());
        fields.put("tx_ref", postData.getTxRef());

       Customization customization = postData.getCustomization();
        String callbackUrl = postData.getCallbackUrl();
        String subAccountId = postData.getSubAccountId();

        if (Util.notNullAndEmpty(subAccountId)) {
            fields.put("subaccount[id]", subAccountId);
        }

        if (Util.notNullAndEmpty(callbackUrl)) {
            fields.put("callback_url", callbackUrl);
        }

        if (customization != null) {
            // TODO: consider directly adding all values to fields map
            if (Util.notNullAndEmpty(customization.getTitle())) {
                fields.put("customization[title]", customization.getTitle());
            }

            if (Util.notNullAndEmpty(customization.getDescription())) {
                fields.put("customization[description]", customization.getDescription());
            }

            if (Util.notNullAndEmpty(customization.getLogo())) {
                fields.put("customization[logo]", customization.getLogo());
            }
        }

        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", fields, SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        InitializeResponseData initializeResponseData = Util.jsonToInitializeResponseData(responseBody)
                .setRawJson(responseBody)
                .setStatusCode(statusCode);

        return initializeResponseData;
    }

    /**
     * @param jsonData JSON data which contains post fields.
     * @return The current invoking object.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */

    public InitializeResponseData initialize(String jsonData) throws Throwable {
        responseBody = chapaClient.post(BASE_URL + "/transaction/initialize", jsonData, SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        InitializeResponseData initializeResponseData = Util.jsonToInitializeResponseData(responseBody)
                .setRawJson(responseBody)
                .setStatusCode(statusCode);

        return initializeResponseData;
    }

    /**
     * @param transactionRef Unique transaction reference which was associated
     *                       with tx_ref field in post data.
     * @return The current invoking object.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public VerifyResponseData verify(String transactionRef) throws Throwable {
        if (!Util.notNullAndEmpty(transactionRef)) {
            throw new IllegalArgumentException("Transaction reference can't be null or empty");
        }
        responseBody = chapaClient.get(BASE_URL + "/transaction/verify/" + transactionRef, SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        VerifyResponseData verifyResponseData = Util.jsonToVerifyResponseData(responseBody)
                .setRawJson(responseBody)
                .setStatusCode(statusCode);

        return verifyResponseData;
    }

    /**
     * @return List of banks supported by Chapa
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public List<Bank> banks() throws Throwable {
        responseBody = chapaClient.get(BASE_URL + "/banks", SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        return Util.extractBanks(responseBody);
    }

    /**
     * @param subAccount Object of {@link SubAccount} instantiated with
     *                   post fields
     * @return The current invoking object.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(SubAccount subAccount) throws Throwable {
        Map<String, Object> fields = new HashMap<>();
        fields.put("business_name", subAccount.getBusinessName());
        fields.put("account_name", subAccount.getAccountName());
        fields.put("account_number", subAccount.getAccountNumber());
        fields.put("bank_code", subAccount.getBankCode());
        fields.put("split_type", subAccount.getSplitType().name().toLowerCase());
        fields.put("split_value", subAccount.getSplitValue());
        responseBody = chapaClient.post(BASE_URL + "/subaccount", fields, SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        SubAccountResponseData subAccountResponseData = Util.jsonToSubAccountResponseData(responseBody)
                .setRawJson(responseBody)
                .setStatusCode(statusCode);

        return subAccountResponseData;
    }

    /**
     * @param jsonData JSON data which contains post fields.
     * @return The current invoking object.
     * @throws Throwable Throws an exception for failed request to Chapa API.
     */
    public SubAccountResponseData createSubAccount(String jsonData) throws Throwable {
        responseBody = chapaClient.post(BASE_URL + "/subaccount", jsonData, SECRETE_KEY);
        statusCode = chapaClient.getStatusCode();

        SubAccountResponseData subAccountResponseData = Util.jsonToSubAccountResponseData(responseBody)
                .setRawJson(responseBody)
                .setStatusCode(statusCode);

        return subAccountResponseData;
    }

}
