package com.yaphet.chapa;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.SplitType;
import com.yaphet.chapa.model.SubAccount;

/**
 * The Util class serves as a helper class for the main {@link com.yaphet.chapa.Chapa} class.
 */
public class Util {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Clock CLOCK = Clock.systemDefaultZone();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss");
    private final static Gson JSON_MAPPER = new Gson();


    /**
     * @param data Object to be validated for constraint violation.
     */
    static <T> void validate(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't validate null object");
        }

        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(data);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMsg.append(violation.getMessage()).append(", ");
            }
            throw new ValidationException(errorMsg.toString());
        }
    }

    /**
     * @param jsonData JSON data that contains post fields to be mapped
     *                 to PostData object.
     * @return A PostData object which contains post fields of the
     * provided JSON data.
     */
    public static PostData mapJsonToPostData(String jsonData) {
        if (!notNullAndEmpty(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to PostData object");
        }

        Map<String, String> newMap = jsonToMap(jsonData);
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        Type bankListType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> customizations = JSON_MAPPER.fromJson(jsonObject.get("customizations"), bankListType);
        return PostData.builder()
                .amount(new BigDecimal(String.valueOf(newMap.get("amount"))))
                .currency(newMap.get("currency"))
                .email(newMap.get("email"))
                .firstName(newMap.get("first_name"))
                .lastName(newMap.get("last_name"))
                .txRef(newMap.get("tx_ref"))
                .callbackUrl(newMap.get("callback_url"))
                .customizations(customizations)
                .build();
    }

    /**
     * @param jsonData JSON data that contains post fields to be mapped
     *                 to PostData object.
     * @return A PostData object which contains post fields of the
     * provided JSON data.
     */
    public static SubAccount mapJsonToSubAccount(String jsonData) {
        if (!notNullAndEmpty(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to SubAccount object");
        }

        Map<String, String> newMap = jsonToMap(jsonData);
        return SubAccount.builder()
                .businessName(newMap.get("business_name"))
                .accountName(newMap.get("account_name"))
                .accountNumber(newMap.get("account_number"))
                .bankCode(newMap.get("bank_code"))
                .splitType(SplitType.valueOf(newMap.get("split_type")))
                .splitValue(Double.valueOf(String.valueOf(newMap.get("split_value"))))
                .build();
    }


    /**
     * @param jsonData JSON data to be mapped to Map object.
     * @return A Map object which contains post fields of the provided
     * JSON data.
     */
    public static Map<String, String> jsonToMap(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, Map.class);
    }

    static List<Bank> extractBanks(String jsonData) {
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        Type bankListType = new TypeToken<List<Bank>>() {
        }.getType();
        return JSON_MAPPER.fromJson(jsonObject.get("data"), bankListType);
    }

    /**
     * @return A random string followed by the current date/time value (dd-MM-yy-HH-mm-ss).
     */
    public static String generateToken() {
        final LocalDateTime now = LocalDateTime.now(CLOCK);
        return UUID.randomUUID().toString().substring(0, 8) + "_" + FORMATTER.format(now);
    }

    static boolean notNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
