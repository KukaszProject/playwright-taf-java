package com.demo.taf.ui.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final String emailInput = "[data-qa='login-email']";
    private final String passwordInput = "[data-qa='login-password']";
    private final String loginButton = "[data-qa='login-button']";
    private final String loggedinAsText = "text=Logged in as";
    private final String errorMessage = "text=Your email or password is incorrect!";

    public LoginPage(Page page) {
        super(page);
    }
    
    public LoginPage load(String baseUrl) {
        navigateTo(baseUrl + "/login");
        acceptCookiesIfPresent();
        return this;
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        page.locator(loginButton).click();
    }

    public boolean isLoginSuccessful() {
        return isVisible(loggedinAsText);
    }

    public boolean isErrorMessageDisplayed() {
        return isVisible(errorMessage);
    }
}
