package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserChecks {
    @Step("Check delete is successfully")
    public void deleteSuccessFully(ValidatableResponse deleteResponse) {
        boolean deleted = deleteResponse
                .assertThat()
                .statusCode(HTTP_ACCEPTED)
                .extract()
                .path("success");
        assertTrue(deleted);
    }

    @Step("Check create user is successfully")
    public String createdSuccessfully(ValidatableResponse createResponse) {
        String created = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
        assertNotNull(created);
        return created;
    }
}
