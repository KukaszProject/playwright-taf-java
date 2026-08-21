package com.demo.taf.ui.pages;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    private final String loginLink = "a[href='/login']";
    private final String emailInput = "[data-qa='login-email']";
    private final String passwordInput = "[data-qa='login-password']";
    private final String loginButton = "[data-qa='login-button']";
    private final String loggedinAsText = "text=Logged in as";
    private final String errorMessage = "text=Your email or password is incorrect!";

    public LoginPage(Page page) {
        super(page);
    }
    
    @Step("Navigating to the login page")
    public LoginPage navigateToLogin() {
        acceptCookiesIfPresent();
        page.locator(loginLink).click();
        return this;
    }
    
    @Step("Logging in with email: {email}")
    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        page.locator(loginButton).click();
    }

    @Step("Checking if login was successful")
    public boolean isLoginSuccessful() {
        return isVisible(loggedinAsText);
    }

    @Step("Checking if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return isVisible(errorMessage);
    }

    @Step("Retrieving error message text")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step("Retrieving logged-in as text")
    public String getLoggedInAsText() {
        return getText(loggedinAsText);
    }

    @Step("Checking if login button is visible")
    public boolean isLoginButtonVisible() {
        return isVisible(loginButton);
    }
}
