package com.yaphet.chapa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


@Disabled
class ChapaTest {

     private Chapa underTest;

    @BeforeEach
    void setUp() {
        underTest = new Chapa("secrete-key");
    }

    @Test
    void canInitializeTransaction() {
    }

    @Test
    void testInitialize() {
    }

    @Test
    void verify() {
    }

    @Test
    void asString() {
    }

    @Test
    void asMap() {
    }
}