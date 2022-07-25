package com.yaphet.chapa;

import com.google.gson.Gson;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class Util {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();



    public static void validateFields(ApiFields apiFields) {
        validate(apiFields);
    }
    public static void validateFields(String jsonData) {
        ApiFields apiFields = mapJsonToApiField(jsonData);
        validate(apiFields);
    }

    private static void validate(ApiFields apiFields) {
        Set<ConstraintViolation<ApiFields>> violations = VALIDATOR.validate(apiFields);

        if(!violations.isEmpty()){
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<ApiFields> violation : violations) {
                errorMsg.append(violation.getMessage()).append(", ");

            }
            throw new ValidationException(errorMsg.toString());
        }
    }

    private static ApiFields mapJsonToApiField(String jsonData) {
        Map<String, String> newMap = jsonToMap(jsonData);
        return ApiFields.builder()
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
