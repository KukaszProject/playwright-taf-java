package com.demo.taf.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    private PlaywrightManager() {
        // Private constructor to prevent instantiation
    }

    public static void initDriver() {
        if(PLAYWRIGHT.get() == null) {
            Playwright playwright = Playwright.create();
            PLAYWRIGHT.set(playwright);

            Browser browser = BrowserFactory.createBrowser(playwright);
            BROWSER.set(browser);

            BrowserContext context = browser.newContext();
            CONTEXT.set(context);

            Page page = context.newPage();
            PAGE.set(page);
        }
    }

    public static Page getPage() {
        return PAGE.get();
    }

    public static BrowserContext getContext() {
        return CONTEXT.get();
    }

    public static void closeDriver() {
        if (PAGE.get() != null) PAGE.get().close();
        if (CONTEXT.get() != null) CONTEXT.get().close();
        if (BROWSER.get() != null) BROWSER.get().close();
        if (PLAYWRIGHT.get() != null) PLAYWRIGHT.get().close();

        PAGE.remove();
        CONTEXT.remove();
        BROWSER.remove();
        PLAYWRIGHT.remove();
    }
    
}
