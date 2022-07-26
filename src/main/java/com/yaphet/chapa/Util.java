package com.yaphet.chapa;

import com.google.gson.Gson;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * The Util class serves as a helper class for Chapa
 */
public class Util {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     *
     * @param postData  instance of PostData class to be validated
     */
    public static void validatePostData(PostData postData) {
        validate(postData);
    }

    /**
     *
     * @param jsonData  json data to be validated
     */
    public static void validatePostData(String jsonData) {
        PostData postData = mapJsonToPostData(jsonData);
        validate(postData);
    }

    /**
     *
      * @param postData instance of PostData class to be validated
     */
    private static void validate(PostData postData) {
        Set<ConstraintViolation<PostData>> violations = VALIDATOR.validate(postData);

        if(!violations.isEmpty()){
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<PostData> violation : violations) {
                errorMsg.append(violation.getMessage()).append(", ");

            }
            throw new ValidationException(errorMsg.toString());
        }
    }

    /**
     *
     * @param jsonData  json data to be mapped to PostData object
     * @return          instance PostData which contains fields from json data
     */
    private static PostData mapJsonToPostData(String jsonData) {
        Map<String, String> newMap = jsonToMap(jsonData);
        return PostData.builder()
                .amount(new BigDecimal(newMap.get("amount")))
                .currency(newMap.get("currency"))
                .email(newMap.get("email"))
                .first_name(newMap.get("first_name"))
                .last_name(newMap.get("last_name"))
                .tx_ref(newMap.get("tx_ref"))
                .callback_url(newMap.get("callback_url"))
                .customization_title(newMap.get("customization_title"))
                .customization_description(newMap.get("customization_description"))
                .build();
    }
    public static Map<String, String> jsonToMap(String jsonData){
        return new Gson().fromJson(jsonData, Map.class);
    }
}
