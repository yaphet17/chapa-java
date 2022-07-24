package com.yaphet;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yaphet.api.Chapa;

public class App
{
    public static void main( String[] args ) throws UnirestException {

        Chapa chapa = new Chapa();
        chapa.test();
    }
}
