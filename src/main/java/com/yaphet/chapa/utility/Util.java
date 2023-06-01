package com.yaphet.chapa.utility;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.yaphet.chapa.model.*;

/**
 * The <code>Util</code> class serves as a helper class for the main {@link com.yaphet.chapa.Chapa} class.
 */
public class Util {

    private static final Clock CLOCK = Clock.systemDefaultZone();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss");
    private final static Gson JSON_MAPPER = new Gson();

    /**
     * @param jsonData A json string to be mapped to a {@link PostData} object.
     * @return A {@link PostData} object which contains post fields of the
     * provided JSON data.
     */
    public static PostData jsonToPostData(String jsonData) {
        if (!notNullAndEmpty(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to PostData object");
        }

        Map<String, String> newMap = jsonToMap(jsonData);
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        Type bankListType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> customizations = JSON_MAPPER.fromJson(jsonObject.get("customizations"), bankListType);
        Customization customization = new Customization()
                .setTitle(customizations.get("customization[title]"))
                .setTitle(customizations.get("customization[description]"))
                .setTitle(customizations.get("customization[logo]"))
                ;
        return new PostData()
                .setAmount(new BigDecimal(String.valueOf(newMap.get("amount"))))
                .setCurrency(newMap.get("currency"))
                .setEmail(newMap.get("email"))
                .setFirstName(newMap.get("first_name"))
                .setLastName(newMap.get("last_name"))
                .setTxRef(newMap.get("tx_ref"))
                .setCallbackUrl(newMap.get("callback_url"))
                .setCustomization(customization);
    }

    /**
     * @param jsonData A json string to be mapped to a {@link SubAccount} object.
     * @return A {@link SubAccount} object which contains post fields of the
     * provided JSON data.
     */
    public static SubAccount jsonToSubAccount(String jsonData) {
        if (!notNullAndEmpty(jsonData)) {
            throw new IllegalArgumentException("Can't map null or empty json to SubAccount object");
        }

        return JSON_MAPPER.fromJson(jsonData, SubAccount.class);
    }

    /**
     * @param jsonData a json string to be mapped to a Map object.
     * @return A Map object which contains post fields of the provided
     * JSON data.
     */
    public static Map<String, String> jsonToMap(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, Map.class);
    }

    /**
     * @param jsonData a json string to be mapped to an {@link InitializeResponseData} object.
     * @return An {@link InitializeResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static InitializeResponseData jsonToInitializeResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, InitializeResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a {@link VerifyResponseData} object.
     * @return A {@link VerifyResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static VerifyResponseData jsonToVerifyResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, VerifyResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a {@link SubAccountResponseData} object.
     * @return A {@link SubAccountResponseData} object which contains response fields of the provided
     * JSON data.
     */
    public static SubAccountResponseData jsonToSubAccountResponseData(String jsonData) {
        return JSON_MAPPER.fromJson(jsonData, SubAccountResponseData.class);
    }

    /**
     * @param jsonData a json string to be mapped to a list of {@link Bank} objects.
     * @return A list of {@link Bank} objects each containing details of a bank.
     */
    public static List<Bank> extractBanks(String jsonData) {
        JsonObject jsonObject = JSON_MAPPER.fromJson(jsonData, JsonObject.class);
        Type bankListType = new TypeToken<List<Bank>>() {}.getType();

        return JSON_MAPPER.fromJson(jsonObject.get("data"), bankListType);
    }

    /**
     * @return A random string followed by the current date/time value (dd-MM-yy-HH-mm-ss).
     */
    public static String generateToken() {
        final LocalDateTime now = LocalDateTime.now(CLOCK);
        return UUID.randomUUID().toString().substring(0, 8) + "_" + FORMATTER.format(now);
    }

    public static boolean notNullAndEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean is2xxSuccessful(int statucCode) {
        return (statucCode / 100) == 2;
    }
}
