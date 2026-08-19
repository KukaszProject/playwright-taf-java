package com.demo.taf.driver;

import java.util.Map;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {

    private static final ThreadLocal<Playwright> PLAYWRIGHT = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    private static final ThreadLocal<APIRequestContext> API_REQUEST = new ThreadLocal<>();

    private PlaywrightManager() {}

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

    public static void initApiContext(String baseApiUrl, String token) {
        if(PLAYWRIGHT.get() == null) {
            PLAYWRIGHT.set(Playwright.create());
        }

        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", token);

        APIRequestContext requestContext = PLAYWRIGHT.get().request().newContext(
            new APIRequest.NewContextOptions()
            .setBaseURL(baseApiUrl)
            .setExtraHTTPHeaders(headers)
        );
        API_REQUEST.set(requestContext);
    }

    public static Page getPage() {
        return PAGE.get();
    }

    public static BrowserContext getContext() {
        return CONTEXT.get();
    }

    public static APIRequestContext getApiContext() {
        return API_REQUEST.get();
    }

    public static void closeDriver() {
        if (PAGE.get() != null) PAGE.get().close();
        if (CONTEXT.get() != null) CONTEXT.get().close();
        if (BROWSER.get() != null) BROWSER.get().close();
        if (API_REQUEST.get() != null) API_REQUEST.get().dispose();
        if (PLAYWRIGHT.get() != null) PLAYWRIGHT.get().close();

        PAGE.remove();
        CONTEXT.remove();
        BROWSER.remove();
        API_REQUEST.remove();
        PLAYWRIGHT.remove();
    }
    
}
