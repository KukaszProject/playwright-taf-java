package com.demo.taf.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({
    "system:properties",
    "system:env",
    "classpath:environments/${env}.properties",
    "classpath:environments/qa.properties" // Default environment if 'env' property is not set
})

public interface EnvironmentConfig extends Config {
    
    @Key("base.ui.url")
    String baseUiUrl();

    @Key("base.api.url")
    String baseApiUrl();

    @Key("browser")
    @DefaultValue("chromium")
    String browser();

    @Key("headless")
    @DefaultValue("true")
    boolean headless();
}
