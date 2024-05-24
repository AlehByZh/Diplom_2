package org.example.user;

import io.restassured.response.ValidatableResponse;
import org.example.Client;


public class UserClient extends Client {
    private static final String AUTH_PATH = "/auth";

    public ValidatableResponse deleteUser(String created) {
        return spec()
                .header("authorization", created)
                .when()
                .delete(AUTH_PATH + "/user")
                .then().log().all();
    }

    public ValidatableResponse creatUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then().log().all();
    }

}
