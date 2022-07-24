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

    private final String apiKey;
    private final ApiFields apiFields;
    public Chapa(String apiKey, ApiFields apiFields){
        this.apiKey = apiKey;
        this.apiFields = apiFields;

        validateFields();
    }


    public String initialize() throws UnirestException {
        Gson gson = new Gson();

        HttpResponse<JsonNode> response = Unirest.post("https://api.chapa.co/v1/transaction/initialize")
                .header("Accept-Encoding","application/json")
                .header("Authorization", "Bearer " + apiKey)
                .field( "amount", apiFields.getAmount().toString())
                .field( "currency", apiFields.getCurrency())
                .field("first_name", apiFields.getFirst_name())
                .field("last_name", apiFields.getLast_name())
                .field(  "email", apiFields.getEmail())
                .field( "tx_ref", apiFields.getTx_ref())
                .field("customization[title]",  apiFields.getCustomization_title())
                .field("customization[description]", apiFields.getCustomization_description())
                .asJson();

        Map<String, String> responseBody = gson.fromJson(response.getBody().toString(), Map.class);
        return responseBody.toString();
    }

    private void validateFields() {
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
