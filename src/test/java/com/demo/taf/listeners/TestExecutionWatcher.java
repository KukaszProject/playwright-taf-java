package com.demo.taf.listeners;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.demo.taf.driver.PlaywrightManager;

import io.qameta.allure.Allure;

public class TestExecutionWatcher implements TestWatcher{
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        
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
}
