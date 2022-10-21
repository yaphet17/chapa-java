package com.yaphet.chapa;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import com.google.gson.Gson;

/**
 * The Util class serves as a helper class for the main {@link com.yaphet.chapa.Chapa} class.
 */
public class Util {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Clock clock = Clock.systemDefaultZone();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss");


    /**
     *
     * @param postData  Instance of PostData class that contains post
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
     * @param postData Instance of PostData class that contains post
     *                 fields to be validated.
     */
    private static void validate(PostData postData) {
        if(postData == null) {
            throw new IllegalArgumentException("Can't validate null PostData object");
        }

        Set<ConstraintViolation<PostData>> violations = VALIDATOR.validate(postData);
        if(!violations.isEmpty()) {
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
     * @return          A PostData object which contains post fields of the
     *                  provided JSON data.
     */
    private static PostData mapJsonToPostData(String jsonData) {
        if(!notNullAndEmpty(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to PostData object");
        }

        Map<String, String> newMap = jsonToMap(jsonData);
        return PostData.builder()
                .amount(new BigDecimal(newMap.get("amount")))
                .currency(newMap.get("currency"))
                .email(newMap.get("email"))
                .first_name(newMap.get("first_name"))
                .last_name(newMap.get("last_name"))
                .tx_ref(newMap.get("tx_ref"))
                .callback_url(newMap.get("callback_url"))
                .customizations(jsonToMap(newMap.get("customizations")))
                .build();
    }

    /**
     *
     * @param jsonData  JSON data to be mapped to Map object.
     * @return          A Map object which contains post fields of the provided
     *                  JSON data.
     */
    public static Map<String, String> jsonToMap(String jsonData){
        return new Gson().fromJson(jsonData, Map.class);
    }

    /**
     *
     * @return         A random string followed by the current date/time value (dd-MM-yy-HH-mm-ss).
     */
    public static String generateToken() {
        final LocalDateTime now = LocalDateTime.now(clock);
        return UUID.randomUUID().toString().substring(0, 8) + "_" + formatter.format(now);
    }

    static boolean notNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
