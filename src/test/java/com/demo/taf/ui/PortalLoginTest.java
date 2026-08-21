package com.demo.taf.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.demo.taf.base.BaseTest;
import com.demo.taf.ui.pages.LoginPage;

public class PortalLoginTest extends BaseTest {

    @Test
    @DisplayName("UI Negative: Login with invalid password displays site error message")
    void testInvalidPasswordDisplaysError() {
        LoginPage loginPage = new LoginPage(page);
        
        loginPage.navigateToLogin()
                 .login("unregistered_test_user@example.com", "WrongPassword123!");

        assertThat(loginPage.getErrorMessage())
            .as("Authentication error banner should be visible for bad credentials")
            .isEqualTo("Your email or password is incorrect!");
    }

    @Test
    @DisplayName("UI Negative: Empty fields block login submission")
    void testEmptyLoginBlocked() {
        LoginPage loginPage = new LoginPage(page);
        
        loginPage.navigateToLogin()
                 .login("", "");

        assertThat(loginPage.isLoginButtonVisible())
            .as("Login button should remain visible when empty form is submitted")
            .isTrue();
    }
}