package com.yaphet.chapa.client;

import java.util.Map;

public interface ChapaClient {


    String post(String url, Map<String, String>  fields, String body, String secreteKey) throws Throwable;

    String get(String url, String secreteKey) throws Throwable;
}
