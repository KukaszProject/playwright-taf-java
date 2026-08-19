package com.demo.taf.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.demo.taf.driver.PlaywrightManager;

public class ApiBaseTest {
    protected static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();

    @BeforeEach
    public void setUpApi() {
        PlaywrightManager.initApiContext(CONFIG.baseApiUrl());
    }

    @AfterEach
    public void tearDownApi() {
        PlaywrightManager.closeDriver();
    }
    
}
