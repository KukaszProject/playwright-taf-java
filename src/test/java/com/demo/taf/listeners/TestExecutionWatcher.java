package com.demo.taf.listeners;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.taf.driver.PlaywrightManager;

import io.qameta.allure.Allure;

public class TestExecutionWatcher implements TestWatcher{

    private static final Logger logger = LoggerFactory.getLogger(TestExecutionWatcher.class);
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.info("TEST FAILED: {} - {}", context.getDisplayName(), cause.getMessage());
        
        
        if(PlaywrightManager.getPage() != null) {
            byte[] screenshot = PlaywrightManager.getPage().screenshot();
            Allure.addAttachment(
                "Failure Screenshot: " + context.getDisplayName(),
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
            );
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("TEST PASSED: {}", context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.info("TEST ABORTED: {} - {}", context.getDisplayName(), cause.getMessage());
    }
}
