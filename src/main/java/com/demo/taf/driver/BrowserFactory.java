package com.demo.taf.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    public static Browser createBrowser(Playwright playwright) {
        String browserName = System.getProperty("browser", "chromium").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(headless);

        return switch (browserName) {
            case "chromium" -> playwright.chromium().launch(launchOptions);
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }
}