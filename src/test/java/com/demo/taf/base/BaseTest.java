package com.demo.taf.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.demo.taf.driver.PlaywrightManager;
import com.demo.taf.listeners.TestExecutionWatcher;

@ExtendWith(TestExecutionWatcher.class)
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
