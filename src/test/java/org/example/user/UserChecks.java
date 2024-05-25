package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

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

    @Step("Check creat existing user unsuccessfully")
    public boolean createdUnsuccessfully(ValidatableResponse createResponse) {
        boolean uncreated = createResponse
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .extract()
                .path("success");
        assertFalse(uncreated);
        return uncreated;
    }

    @Step("Check authorization is successfully")
    public String authSuccessfully(ValidatableResponse authResponse) {
        String authed = authResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("accessToken");
        assertNotNull(authed);
        return authed;
    }

    @Step("Check authorization is unsuccessfully")
    public boolean authUnsuccessfully(ValidatableResponse createResponse) {
        boolean unauth = createResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(unauth);
        return unauth;
    }

    @Step("Check is name updated")
    public String updatedNameSuccessfully(ValidatableResponse updateResponse, String newName) {
        String newUserName = updateResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("user.name");
        assertEquals(newName, newUserName);
        return newUserName;
    }

    @Step("Check is email updated")
    public String updatedEmailSuccessfully(ValidatableResponse updateResponse, String newEmail) {
        String newUserEmail = updateResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("user.email");
        assertEquals(newEmail, newUserEmail);
        return newUserEmail;
    }

    @Step("Check data is don't updated")
    public boolean updatedDataUnsuccessfully(ValidatableResponse updateResponse) {
        boolean unupdated = updateResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(unupdated);
        return unupdated;
    }
}
