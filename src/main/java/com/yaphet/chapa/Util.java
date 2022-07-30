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
 * The Util class serves as a helper class for the main {@link com.yaphet.chapa.Chapa} class.
 */
public class Util {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     *
     * @param postData  instance of PostData class that contains post
     *                  fields to be validated.
     */
    public static void validatePostData(PostData postData) {
        validate(postData);
    }

    /**
     *
     * @param jsonData  JSON data that contains post fields to be validated.
     */
    public static void validatePostData(String jsonData) {
        PostData postData = mapJsonToPostData(jsonData);
        validate(postData);
    }

    /**
     *
     * @param postData instance of PostData class that contains post
     *                 fields to be validated.
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
     * @param jsonData  JSON data that contains post fields to be mapped
     *                  to PostData object.
     * @return          a PostData object which contains post fields of the
     *                  provided JSON data.
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

    /**
     *
     * @param jsonData  JSON data to be mapped to Map<String, String> object.
     * @return          a Map object which contains post fields of the provided
     *                  JSON data.
     */
    public static Map<String, String> jsonToMap(String jsonData){
        return new Gson().fromJson(jsonData, Map.class);
    }
}
