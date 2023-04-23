package com.yaphet.chapa;

import com.yaphet.chapa.model.Customization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.HashMap;
import java.util.Map;

@Disabled
class UtilTest {
    private final Map<String, String> customizations = new HashMap<>();
    private final Customization customization = new Customization();

    @BeforeEach
    public void setUp() {
        customization.setTitle("E-commerce");
        customization.setDescription("It is time to pay");
        customization.setLogo("https://mylogo.com/log.png");
    }
}