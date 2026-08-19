package com.demo.taf.config;

import org.aeonbits.owner.Config;

public interface EnvironmentConfig extends Config {

    @Key("base.ui.url")
    String baseUiUrl();

    @Key("base.api.url")
    String baseApiUrl();

    @Key("api.token")
    String apiToken();

    @Key("browser")
    @DefaultValue("chromium")
    String browser();

    @Key("headless")
    @DefaultValue("true")
    boolean headless();
}