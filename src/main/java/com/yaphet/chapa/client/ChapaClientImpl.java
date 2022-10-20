package com.yaphet.chapa.client;

import java.util.Map;

import org.apache.http.HttpHeaders;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class ChapaClientImpl implements ChapaClient{

    private static final String authorizationHeader = HttpHeaders.AUTHORIZATION;
    private static final String acceptEncodingHeader = HttpHeaders.ACCEPT_ENCODING;

    @Override
    public String post(String url, Map<String, String> fields, String body, String secreteKey) throws Throwable {
        HttpRequestWithBody request = Unirest.post(url)
                .header(acceptEncodingHeader, "application/json")
                .header(authorizationHeader, "Bearer " + secreteKey);

        if(fields != null && !fields.isEmpty()) {
            fields.forEach((k, v) -> {
                request.field(k, v);
            });
        }

        if(body != null && !body.isEmpty()) {
            request.body(body);
        }

        return request.asJson().getBody().toString();
    }

    @Override
    public String get(String url, String secreteKey) throws Throwable{
        return Unirest.get(url)
                .header(acceptEncodingHeader,"application/json")
                .header(authorizationHeader, "Bearer " + secreteKey)
                .asJson().getBody().toString();
    }
}
