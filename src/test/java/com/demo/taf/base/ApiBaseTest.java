package com.demo.taf.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;
import com.demo.taf.driver.PlaywrightManager;
import com.demo.taf.listeners.TestExecutionWatcher;

@ExtendWith(TestExecutionWatcher.class)
public abstract class ApiBaseTest {
    protected static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();
    protected static final Logger logger = LoggerFactory.getLogger(ApiBaseTest.class);

    @BeforeEach
    public void setUpApi(TestInfo testInfo) {
        logger.info("Running API test: {}", testInfo.getDisplayName());
        logger.info("Initializing API context...");
        PlaywrightManager.initApiContext(CONFIG.baseApiUrl(), CONFIG.apiToken());
    }

    @AfterEach
    public void tearDownApi() {
        logger.info("Tearing down API context...");
        PlaywrightManager.closeDriver();
    }
    
}
