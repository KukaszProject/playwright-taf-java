package com.demo.taf.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.demo.taf.config.ConfigFactory;
import com.demo.taf.config.EnvironmentConfig;

public class BrowserFactory {
    private static final EnvironmentConfig CONFIG = ConfigFactory.getConfig();

    public static Browser createBrowser(Playwright playwright) {
        String browserName = CONFIG.browser().toLowerCase();

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(CONFIG.headless());

        return switch (browserName) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }
}