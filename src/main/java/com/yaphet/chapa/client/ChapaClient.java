package com.yaphet.chapa.client;

import java.util.Map;

public interface ChapaClient {


    String post(String url, Map<String, Object> fields, String secreteKey) throws Throwable;

    String post(String url, String body, String secreteKey) throws Throwable;

    String get(String url, String secreteKey) throws Throwable;

    int getStatusCode();
}
