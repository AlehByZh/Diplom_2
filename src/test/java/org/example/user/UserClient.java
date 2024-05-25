package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

import java.util.Map;


public class UserClient extends Client {
    private static final String AUTH_PATH = "/auth";

    @Step("Create user")
    public ValidatableResponse deleteUser(String created) {
        return spec()
                .header("authorization", created)
                .when()
                .delete(AUTH_PATH + "/user")
                .then().log().all();
    }

    @Step("Delete user")
    public ValidatableResponse creatUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then().log().all();
    }

    @Step("Authorization user")
    public ValidatableResponse authUser(UserCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(AUTH_PATH + "/login")
                .then().log().all();
    }

    @Step("Authorization with wrong data")
    public ValidatableResponse authUserWithWrongData(String email, String password) {
        return spec()
                .body(Map.of("email", email, "password", password))
                .when()
                .post(AUTH_PATH + "/login")
                .then().log().all();
    }
}
