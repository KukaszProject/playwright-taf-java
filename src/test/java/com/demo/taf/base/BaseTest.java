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
import com.microsoft.playwright.Page;

@ExtendWith(TestExecutionWatcher.class)
public abstract class BaseTest {
    
    protected static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected Page page;

@BeforeEach
    public void setUp(TestInfo testInfo) {
        logger.info("▶️ STARTING TEST: {}", testInfo.getDisplayName());
        logger.info("Initializing Playwright Browser...");
        PlaywrightManager.initDriver();
        page = PlaywrightManager.getPage();
        
        page.route("**/*googlesyndication.com/**", route -> route.abort());
        page.route("**/*doubleclick.net/**", route -> route.abort());
        
        logger.info("Navigating to base UI URL: {}", CONFIG.baseUiUrl());
        page.navigate(CONFIG.baseUiUrl());
    }

    @AfterEach
    public void tearDown() {
        logger.info("Tearing down Playwright Browser...");
        PlaywrightManager.closeDriver();
    }
}
