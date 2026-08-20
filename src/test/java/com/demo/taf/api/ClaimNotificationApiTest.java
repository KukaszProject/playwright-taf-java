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

    @Test
    @DisplayName("API: Retrieve Existing Claim (GET) successfully")
    void testGetClaimDetails() {
        APIResponse response = PlaywrightManager.getApiContext().get("/posts/1");

        assertThat(response.status())
            .as("Expected HTTP 200 OK for existing claim retrieval")
            .isEqualTo(200);

        assertThat(response.text())
            .as("Response should contain the userId for the claim")
            .contains("userId");
    }

    @Test
    @DisplayName("API: Update Existing Claim (PUT) successfully")
    void testUpdateClaimStatus() {
        String updatePayload = """
                {
                    "id": 1,
                    "title": "Updated: Claim Title",
                    "body": "Reserve increased to 3.0M USD due to severe roof damage",
                    "userId": 1
                }
                """;

        APIResponse response = PlaywrightManager.getApiContext()
            .put("/posts/1", RequestOptions.create().setData(updatePayload));

        assertThat(response.status())
            .as("Expected HTTP 200 OK for claim update")
            .isEqualTo(200);
        assertThat(response.text())
            .as("Response should contain the updated claim title")
            .contains("Updated: Claim Title");
    }

    @Test
    @DisplayName("API: Delete Processed Claim (DELETE) successfully")
    void testDeleteClaim() {
        APIResponse response = PlaywrightManager.getApiContext().delete("/posts/1");

        assertThat(response.status())
            .as("Expected HTTP 200 OK for claim deletion")
            .isEqualTo(200);
    }

    @Test
    @DisplayName("API Negative: Fetch Non-Existent Claim (GET) should return 404")
    void testFetchNonExistentClaim() {
        APIResponse response = PlaywrightManager.getApiContext().get("/posts/9999");

        assertThat(response.status())
            .as("Expected HTTP 404 Not Found for non-existent claim")
            .isEqualTo(404);
    }
}
