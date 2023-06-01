package com.yaphet.chapa;

import com.google.gson.Gson;
import com.yaphet.chapa.client.ChapaClientImpl;
import com.yaphet.chapa.model.*;
import com.yaphet.chapa.utility.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ChapaTest {

    private final Gson gson = new Gson();
    private ChapaClientImpl chapaClient;
    private Chapa underTest;
    private PostData postData;
    private String postDataString;
    private SubAccount subAccount;
    private String subAccountString;

    @BeforeEach
    void setUp() {
        chapaClient = mock(ChapaClientImpl.class);
        underTest = new Chapa(chapaClient, "secrete-key");
        Customization customization = new Customization()
                .setTitle("E-commerce")
                .setDescription("It is time to pay")
                .setLogo("https://mylogo.com/log.png");
        postData = new PostData()
                .setAmount(new BigDecimal("100"))
                .setCurrency("ETB")
                .setFirstName("Abebe")
                .setLastName("Bikila")
                .setEmail("abebe@bikila.com")
                .setTxRef(Util.generateToken())
                .setCallbackUrl("https://chapa.co")
                .setReturnUrl("https://chapa.co")
                .setSubAccountId("testSubAccountId")
                .setCustomization(customization);
        postDataString = " { " +
                "'amount': '100', " +
                "'currency': 'ETB'," +
                "'email': 'abebe@bikila.com'," +
                "'first_name': 'Abebe'," +
                "'last_name': 'Bikila'," +
                "'tx_ref': 'tx-myecommerce12345'," +
                "'callback_url': 'https://chapa.co'," +
                "'return_url': 'https://chapa.co'," +
                "'subaccount[id]': 'testSubAccountId'," +
                "'customizations':{'customization[title]':'E-commerce','customization[description]':'It is time to pay','customization[logo]':'https://mylogo.com/log.png'}" +
                " }";
        subAccount = new SubAccount()
                .setBusinessName("Abebe Suq")
                .setAccountName("Abebe Bikila")
                .setAccountNumber("0123456789")
                .setBankCode("001")
                .setSplitType(SplitType.PERCENTAGE)
                .setSplitValue(0.2);
        subAccountString = "{'business_name':'Abebe Suq','bank_code':'001','account_name':'Abebe Bikila','account_number':'0123456789','split_type':'PERCENTAGE','split_value':0.2}";
    }

    // This is not really a unit test ):
    @Test
    public void shouldInitializeTransaction_asString() throws Throwable {
        // given
        String expectedResponse = "{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        String actualResponse = underTest.initialize(postData).asString();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldInitializeTransaction_asResponseData() throws Throwable {
        // given
        InitializeResponseData expectedResponseData = new InitializeResponseData()
                .setMessage("Transaction reference has been used before")
                .setStatus("failed")
                .setStatusCode(200)
                .setData(new InitializeResponseData.Data().setCheckOutUrl("https://checkout.chapa.co/checkout/payment/somestring"));

        String expectedResponse = "{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Transaction reference has been used before\",\"status\":\"failed\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        InitializeResponseData actualResponseData = underTest.initialize(postData);

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(gson.toJson(expectedResponseData), gson.toJson(actualResponseData), false);
    }

    @Test
    public void shouldInitializeTransaction_With_Json_Input() throws Throwable {
        // given
        String expectedResponse = "{\"data\":{\"checkout_url\":\"https://checkout.chapa.co/checkout/payment/somestring\"},\"message\":\"Hosted Link\",\"status\":\"success\"}";

        // when
        when(chapaClient.post(anyString(), anyString(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        String actualResponse = underTest.initialize(postDataString).asString();

        // then
        verify(chapaClient).post(anyString(), anyString(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldVerifyTransaction_asString() throws Throwable {
        // given
        String expectedResponse = "{\"data\":null,\"message\":\"Payment not paid yet\",\"status\":\"null\"}";

        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        String actualResponse = underTest.verify("test-transaction").asString();

        // then
        verify(chapaClient).get(anyString(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldVerifyTransaction_asResponseData() throws Throwable {
        // given
        VerifyResponseData expectedResponseData = new VerifyResponseData()
                .setMessage("Payment not paid yet")
                .setStatus("null")
                .setStatusCode(200)
                .setData(null);
        String expectedResponse = "{\"data\":null,\"message\":\"Payment not paid yet\",\"status\":\"null\"}";

        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        VerifyResponseData actualResponseData = underTest.verify("test-transaction");

        // then
        verify(chapaClient).get(anyString(), anyString());
        JSONAssert.assertEquals(gson.toJson(expectedResponseData), gson.toJson(actualResponseData), false);
    }


    @Test
    public void shouldGetListOfBanks() throws Throwable {
        // given
        String expectedResponse = "{\"data\":[{\"updated_at\":\"2022-07-04T21:34:03.000000Z\",\"name\":\"Awash Bank\",\"created_at\":\"2022-03-17T04:21:30.000000Z\",\"id\":\"80a510ea-7497-4499-8b49-ac13a3ab7d07\",\"country_id\":1}],\"message\":\"Banks retrieved\"}";
        List<Bank> expectedBanks = new ArrayList<>();
        expectedBanks.add(new Bank()
                .setId("80a510ea-7497-4499-8b49-ac13a3ab7d07")
                .setName("Awash Bank")
                .setCountryId(1)
                .setCreatedAt("2022-03-17T04:21:30.000000Z")
                .setUpdatedAt("2022-07-04T21:34:03.000000Z"));
        // when
        when(chapaClient.get(anyString(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
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
        when(chapaClient.getStatusCode()).thenReturn(200);
        String actualResponse = underTest.createSubAccount(subAccount).asString();

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldCreateSubAccount_With_Json_Input() throws Throwable {
        // given
        String expectedResponse = "{\"data\":null,\"message\":\"The Bank Code is incorrect please check if it does exist with our getbanks endpoint.\",\"status\":\"failed\"}";

        // when
        when(chapaClient.post(anyString(), anyString(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        String actualResponse = underTest.createSubAccount(subAccountString).asString();

        // then
        verify(chapaClient).post(anyString(), anyString(), anyString());
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    @Test
    public void shouldCreateSubAccount_asResponseData() throws Throwable {
        // given
        SubAccountResponseData expectedResponseData = new SubAccountResponseData()
                .setMessage("The Bank Code is incorrect please check if it does exist with our getbanks endpoint.")
                .setStatus("failed")
                .setStatusCode(200)
                .setData(null);
        String expectedResponse = "{\"data\":null,\"message\":\"The Bank Code is incorrect please check if it does exist with our getbanks endpoint.\",\"status\":\"failed\"}";

        // when
        when(chapaClient.post(anyString(), anyMap(), anyString())).thenReturn(expectedResponse);
        when(chapaClient.getStatusCode()).thenReturn(200);
        SubAccountResponseData actualResponse = underTest.createSubAccount(subAccount);

        // then
        verify(chapaClient).post(anyString(), anyMap(), anyString());
        JSONAssert.assertEquals(gson.toJson(expectedResponseData), gson.toJson(actualResponse), false);
    }


    // This should not run in the pipeline
    @Test
    @Disabled
    public void testDefault() throws Throwable {
        // given
        Customization customization = new Customization()
                .setTitle("E-commerce")
                .setDescription("It is time to pay")
                .setLogo("https://mylogo.com/log.png");
        PostData postData = new PostData()
                .setAmount(new BigDecimal("100"))
                .setCurrency("ETB")
                .setFirstName("Abebe")
                .setLastName("Bikila")
                .setEmail("abebe@bikila")
                .setTxRef(Util.generateToken())
                .setCallbackUrl("https://chapa.co")
                .setReturnUrl("https://chapa.co")
                .setSubAccountId("testSubAccountId")
                .setCustomization(customization);
        subAccount = new SubAccount()
                .setBusinessName("Abebe Suq")
                .setAccountName("Abebe Bikila")
                .setAccountNumber("0123456789")
                .setBankCode("96e41186-29ba-4e30-b013-2ca36d7e7025")
                .setSplitType(SplitType.PERCENTAGE)
                .setSplitValue(0.2);

        String formData = gson.toJson(postData);
        System.out.println(formData);

        Chapa chapa = new Chapa("");
        List<Bank> banks = chapa.banks();
        banks.forEach(bank -> System.out.println("Bank name: " + bank.getName() + " Bank Code: " + bank.getId()));
        System.out.println("Create SubAccount response: " + chapa.createSubAccount(subAccount).asString());
        System.out.println("Initialize response with PostData: " + chapa.initialize(postData).asString());
        System.out.println("Initialize response with JsonData: " + chapa.initialize(formData).asString());
        System.out.println("Verify response: " + chapa.verify(postData.getTxRef()).asString());
    }

}