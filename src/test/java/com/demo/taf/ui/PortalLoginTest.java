package com.demo.taf.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.demo.taf.base.BaseTest;
import com.demo.taf.driver.PlaywrightManager;
import com.demo.taf.ui.pages.LoginPage;

public class PortalLoginTest extends BaseTest {
    
    @Test
    @DisplayName("User should be able to login successfully with valid credentials")
    void testValidLogin() {
        LoginPage loginPage = new LoginPage(PlaywrightManager.getPage());
        loginPage.load(CONFIG.baseUiUrl());
        loginPage.login("test@example.com", "password123");

        assertThat(loginPage.isErrorMessageDisplayed())
            .as("Error message should not be displayed for valid login")
            .isTrue();
        
    }

}
