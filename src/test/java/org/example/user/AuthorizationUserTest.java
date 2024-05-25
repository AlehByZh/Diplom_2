package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class AuthorizationUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userAuthorization;
    private boolean userDontAuth;

    @After
    public void deleteUser() {
        if (userAuthorization != null && !userAuthorization.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userAuthorization);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @DisplayName("User authorization positive path")
    @Test
    public void userAuthorizationPositivePath() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        check.createdSuccessfully(createResponse);

        UserCredentials creds = UserCredentials.from(user);
        ValidatableResponse authResponse = client.authUser(creds);
        userAuthorization = check.authSuccessfully(authResponse);
    }

    @DisplayName("User authorization path with non-existent data")
    @Test
    public void userAuthorizationNonExistentPath() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
        UserCredentials creds = UserCredentials.from(user);
        ValidatableResponse authResponse = client.authUser(creds);
        userDontAuth = check.authUnsuccessfully(authResponse);
    }

    @DisplayName("User authorization path with wrong password")
    @Test
    public void userAuthorizationWrongEmail() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userAuthorization = check.createdSuccessfully(createResponse);
        ValidatableResponse authResponse = client.authUserWithWrongData(user.getEmail(),"1234");
        userDontAuth = check.authUnsuccessfully(authResponse);
    }

    @DisplayName("User authorization path with wrong email")
    @Test
    public void userAuthorizationWrongPassword() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userAuthorization = check.createdSuccessfully(createResponse);
        ValidatableResponse authResponse = client.authUserWithWrongData("qucksilver@maximoff.by", user.getPassword());
        userDontAuth = check.authUnsuccessfully(authResponse);
    }
}
