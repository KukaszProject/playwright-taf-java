package com.demo.taf.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.demo.taf.api.models.ClaimPayload;
import com.demo.taf.base.ApiBaseTest;
import com.demo.taf.driver.PlaywrightManager;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

public class ClaimNotificationApiTest extends ApiBaseTest {
    
    @Test
    @DisplayName("API: Submit First Notice of Loss (FNOL) successfully")
    void testSubmitFnolClaim() {

        ClaimPayload newClaim = new ClaimPayload(
            "Global Corp Inc.",
            "Property Damage",
            "TR-2026-991A",
            150000.00
        );

        APIResponse response = PlaywrightManager.getApiContext()
            .post("/posts", RequestOptions.create().setData(newClaim)
        );

        assertThat(response.status())
            .as("Expected HTTP 201 Created for new claim submission")
            .isEqualTo(201);

        String responseBody = response.text();
        assertThat(responseBody)
            .as("Response body should contain the submitted Treaty/Policyholder name")
            .contains("Global Corp Inc.")
            .as("Response should contain an generated ID for the claim")
            .contains("id");
    }
}
