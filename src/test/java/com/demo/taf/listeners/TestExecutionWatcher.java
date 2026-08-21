package com.demo.taf.listeners;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.taf.driver.PlaywrightManager;
import com.microsoft.playwright.Page;

import io.qameta.allure.Allure;

public class TestExecutionWatcher implements AfterTestExecutionCallback {

    private static final Logger logger = LoggerFactory.getLogger(TestExecutionWatcher.class);
    
    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testFailed = context.getExecutionException().isPresent();
        Page page = PlaywrightManager.getPage();

        if (page == null) return;

        Path videoPath = null;
        try {
            videoPath = page.video().path();
        } catch (Exception e) {
            logger.warn("Video path not found.");
        }

        if (testFailed) {
            logger.error("❌ TEST FAILED: {} - Saving Artifacts...", context.getDisplayName());
            try {
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");

                PlaywrightManager.closeDriver();

                if (videoPath != null && Files.exists(videoPath)) {
                    try (InputStream videoStream = Files.newInputStream(videoPath)) {
                        Allure.addAttachment("Execution Video", "video/webm", videoStream, ".webm");
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to attach artifacts to Allure: ", e);
            }
        } else {
            logger.info("✅ TEST PASSED: {} - Deleting Video", context.getDisplayName());
            
            PlaywrightManager.closeDriver();
            
            try {
                if (videoPath != null) {
                    Files.deleteIfExists(videoPath);
                }
            } catch (Exception e) {
                logger.warn("Could not delete passing test video.");
            }
        }
    }
}
