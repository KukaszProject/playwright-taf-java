package com.demo.taf.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.demo.taf.driver.PlaywrightManager;

public abstract class BaseTest {
    
    protected static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();

    @BeforeEach
    public void setUp() {
        PlaywrightManager.initDriver();
    }

    @AfterEach
    public void tearDown() {
        PlaywrightManager.closeDriver();
    }
}
