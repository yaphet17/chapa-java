package com.yaphet.chapa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import com.google.gson.Gson;
import com.yaphet.chapa.client.ChapaClientImpl;
import com.yaphet.chapa.model.Bank;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.SplitType;
import com.yaphet.chapa.model.SubAccount;


@ExtendWith(MockitoExtension.class)
class ChapaTest {

    private Gson gson = new Gson();
    private ChapaClientImpl chapaClient;
    private Chapa underTest;
    private PostData postData;
    private SubAccount subAccount;

    @BeforeEach
    void setUp() {
        chapaClient = mock(ChapaClientImpl.class);
        underTest = new Chapa(chapaClient, "secrete-key");
        Map<String, String> customizations = new HashMap<>();
        customizations.put("customization[title]", "E-commerce");
        customizations.put("customization[description]", "It is time to pay");
        customizations.put("customization[logo]", "https://mylogo.com/log.png");
        postData = PostData.builder()
                .amount(new BigDecimal("100"))
                .currency("ETB")
                .firstName("Abebe")
                .lastName("Bikila")
                .email("abebe@bikila.com")
                .txRef("tx-myecommerce12aaa345")
                .callbackUrl("https://chapa.co")
                .customizations(customizations)
                .build();
        subAccount = SubAccount.builder()
                .businessName("Abebe Suq")
                .accountName("Abebe Bikila")
                .accountNumber("0123456789")
                .bankCode("001")
                .splitType(SplitType.PERCENTAGE)
                .splitValue(0.2)
                .build();
    }

    // This is not really a unit test ):
    @Test
    public void shouldInitializeTransaction_asString() throws Throwable {
        // given
        String expectedResponse = "{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        String actualResponse = underTest.initialize(postData).asString();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldInitializeTransaction_asMap() throws Throwable {
        // given
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("data", null);
        expectedMap.put("message", "Transaction reference has been used before");
        expectedMap.put("status", "failed");
        String expectedResponse = "{\"data\":null,\"message\":\"Transaction reference has been used before\",\"status\":\"failed\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        Map<String, String> actualMap = underTest.initialize(postData).asMap();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        assertEquals(expectedMap.toString(), actualMap.toString());
    }

    @Test
    public void shouldVerifyTransaction_asString() throws Throwable {
        // given
        String expectedResponse = "{\"data\":null,\"message\":\"Payment not paid yet\",\"status\":\"null\"}";

        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        String actualResponse = underTest.verify("test-transaction").asString();

        // then
        verify(chapaClient).get(anyString(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldVerifyTransaction_asMap() throws Throwable {
        // given
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("data", null);
        expectedMap.put("message", "Payment not paid yet");
        expectedMap.put("status", null);
        String expectedResponse = "{\"data\":null,\"message\":\"Payment not paid yet\",\"status\":\"null\"}";

        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        Map<String, String> actualMap = underTest.verify("test-transaction").asMap();

        // then
        verify(chapaClient).get(anyString(), anyString());
        assertEquals(expectedMap.toString(), actualMap.toString());
    }

    @Test
    public void shouldGetListOfBanks() throws Throwable {
        // given
        String expectedResponse = "{\"data\":[{\"updated_at\":\"2022-07-04T21:34:03.000000Z\",\"name\":\"Awash Bank\",\"created_at\":\"2022-03-17T04:21:30.000000Z\",\"id\":\"80a510ea-7497-4499-8b49-ac13a3ab7d07\",\"country_id\":1}],\"message\":\"Banks retrieved\"}";
        List<Bank> expectedBanks = new ArrayList<>();
        expectedBanks.add(Bank.builder()
                        .id("80a510ea-7497-4499-8b49-ac13a3ab7d07")
                        .name("Awash Bank")
                        .countryId(1)
                        .createdAt("2022-03-17T04:21:30.000000Z")
                        .updatedAt("2022-07-04T21:34:03.000000Z")
                        .build());
        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        List<Bank> actualBanks = underTest.banks();

        // then
        verify(chapaClient).get(anyString(), anyString());
        JSONAssert.assertEquals(gson.toJson(expectedBanks), gson.toJson(actualBanks), true);
    }

    @Test
    public void shouldCreateSubAccount_asString() throws Throwable {
        // given
        String expectedResponse = "{\"data\":null,\"message\":\"The Bank Code is incorrect please check if it does exist with our getbanks endpoint.\",\"status\":\"failed\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        String actualResponse = underTest.createSubAccount(subAccount).asString();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldCreateSubAccount_asMap() throws Throwable {
        // given
        String expectedResponse = "{\"data\":null,\"message\":\"The Bank Code is incorrect please check if it does exist with our getbanks endpoint.\",\"status\":\"failed\"}";
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("data", null);
        expectedMap.put("message", "The Bank Code is incorrect please check if it does exist with our getbanks endpoint.");
        expectedMap.put("status", "failed");

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        Map<String, String> actualMap = underTest.createSubAccount(subAccount).asMap();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(gson.toJson(expectedMap), gson.toJson(actualMap), true);
    }

}