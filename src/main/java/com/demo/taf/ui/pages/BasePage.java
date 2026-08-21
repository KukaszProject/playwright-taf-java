package com.demo.taf.ui.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePage {

    protected final Page page;
    private final String cookieConsentBtn = "button.fc-button.fc-cta-consent";

    public BasePage(Page page) {
        this.page = page;
    }
    
    /**
     * Navigates to the specified URL.
     */
    public void navigateTo(String url) {
        page.navigate(url);
        acceptCookiesIfPresent();
    }

    /**
     * Accepts cookies if the cookie consent button is present on the page.
     */
    protected void acceptCookiesIfPresent() {
        Locator consentButton = page.locator(cookieConsentBtn);
        try {
            consentButton.waitFor(new Locator.WaitForOptions().setTimeout(3000));
            if (consentButton.isVisible()) {
                consentButton.click();
            }
        } catch (Exception e) {
            // No cookie consent button found, proceed without action
        }
    }

    /**
     * Types the specified text into the given locator.
     */
    protected void type(String selector, String text) {
        page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator(selector).clear();
        page.locator(selector).fill(text != null ? text : "");
    }

    /**
     * Retrieves the text content of the specified locator.
     */
    protected String getText(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return page.locator(selector).textContent().trim();
    }

    /**
     * Checks if the specified locator is visible on the page.
     */
    protected boolean isVisible(String selector) {
        try {
            return page.locator(selector).isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
