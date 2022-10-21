package com.yaphet.chapa.client;

import java.util.Map;

import org.apache.http.HttpHeaders;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class ChapaClientImpl implements ChapaClient {

    private static final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private static final String acceptEncodingHeader = HttpHeaders.ACCEPT_ENCODING;

    @Override
    public String post(String url, Map<String, Object> fields, String secreteKey) throws Throwable {
        MultipartBody request = Unirest.post(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .fields(fields);


        return request.asJson().getBody().toString();
    }

    @Override
    public String post(String url, String body, String secreteKey) throws Throwable {
        RequestBodyEntity request = Unirest.post(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .body(body);

        return request.asJson().getBody().toString();
    }

    @Override
    public String get(String url, String secreteKey) throws Throwable {
        return Unirest.get(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .asJson().getBody().toString();
    }
}
