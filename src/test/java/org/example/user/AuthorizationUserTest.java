package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class AuthorizationUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userAuthorization;

    @After
    @Step("Delete user")
    public void deleteUser() {
        if (userAuthorization != null && !userAuthorization.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userAuthorization);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @Test
    @DisplayName("User authorization positive path")
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

    @Test
    @DisplayName("User authorization path with non-existent data")
    public void userAuthorizationNonExistentPath() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
        UserCredentials creds = UserCredentials.from(user);
        ValidatableResponse authResponse = client.authUser(creds);
        check.authUnsuccessfully(authResponse);
    }

    @Test
    @DisplayName("User authorization path with wrong password")
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
        check.authUnsuccessfully(authResponse);
    }

    @Test
    @DisplayName("User authorization path with wrong email")
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
        check.authUnsuccessfully(authResponse);
    }
}
