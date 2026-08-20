package com.demo.taf.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.demo.taf.driver.PlaywrightManager;
import com.demo.taf.listeners.TestExecutionWatcher;

@ExtendWith(TestExecutionWatcher.class)
public abstract class BaseTest {
    
    protected static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        logger.info("Initializing Playwright Browser...");
        PlaywrightManager.initDriver();
        logger.info("Navigating to base UI URL: {}", CONFIG.baseUiUrl());
        PlaywrightManager.getPage().navigate(CONFIG.baseUiUrl());
    }

    @AfterEach
    public void tearDown() {
        logger.info("Tearing down Playwright Browser...");
        PlaywrightManager.closeDriver();
    }
}
