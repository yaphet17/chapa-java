package com.yaphet.api;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.validation.*;
import java.util.Map;
import java.util.Set;

public class Chapa {

    private final String secreteKey;
    public Chapa(String secreteKey){
        this.secreteKey = secreteKey;
    }


    public String initialize(ApiFields apiFields) throws UnirestException {
        validateFields(apiFields);
        Gson gson = new Gson();

        HttpResponse<JsonNode> response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer " + secreteKey)
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

        HttpResponse<JsonNode> response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer " + secreteKey)
                .body(jsonData)
                .asJson();
        return response.getBody().toString();
    }

    private void validateFields(ApiFields apiFields) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ApiFields>> violations = validator.validate(apiFields);

        if(!violations.isEmpty()){
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<ApiFields> violation : violations) {
                errorMsg.append(violation.getMessage() + ", ");

            }
            throw new ValidationException(errorMsg.toString());
        }

    }
}
