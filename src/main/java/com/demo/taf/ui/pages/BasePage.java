package com.demo.taf.ui.pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }
    
    /**
     * Navigates to the specified URL.
     */
    public void navigateTo(String url) {
        page.navigate(url);
    }

    /**
     * Types the specified text into the given locator.
     */
    protected void type(String selector, String text) {
        page.locator(selector).clear();
        page.locator(selector).fill(text);
    }

    /**
     * Retrieves the text content of the specified locator.
     */
    protected String getText(String selector) {
        return page.locator(selector).textContent();
    }

    /**
     * Checks if the specified locator is visible on the page.
     */
    protected boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }
}
