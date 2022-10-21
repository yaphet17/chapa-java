package com.yaphet.chapa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import com.yaphet.chapa.client.ChapaClientImpl;


@ExtendWith(MockitoExtension.class)
class ChapaTest {

     private ChapaClientImpl chapaClient;
     private Chapa underTest;
     private PostData postData;

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
                .currency( "ETB")
                .first_name("Abebe")
                .last_name("Bikila")
                .email("abebe@bikila.com")
                .tx_ref("tx-myecommerce12aaa345")
                .callback_url("https://chapa.co")
                .customizations(customizations)
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
        when(chapaClient.get(anyString(),anyString())).thenReturn(expectedResponse);
        String actualResponse = underTest.verify("test-transaction").asString();

        // then
        verify(chapaClient).get(anyString(),anyString());
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
        when(chapaClient.get(anyString(),anyString())).thenReturn(expectedResponse);
        Map<String, String> actualMap = underTest.verify("test-transaction").asMap();

        // then
        verify(chapaClient).get(anyString(), anyString());
        assertEquals(expectedMap.toString(), actualMap.toString());
    }
}