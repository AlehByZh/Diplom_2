package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class RegisterUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userCreated;
    private boolean userDontCreated;

    @Step("Delete user")
    @After
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @DisplayName("Creating new user positive path")
    @Test
    public void registerPositiveTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(createResponse);
    }

    @DisplayName("Creating existing user path")
    @Test
    public void registerExistingTest() {
        var user = User.generic();
        ValidatableResponse createResponse = client.creatUser(user);
        userDontCreated = check.createdUnsuccessfully(createResponse);
    }

    @DisplayName("Creating new user without email path")
    @Test
    public void registerWithoutEmailTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userDontCreated = check.createdUnsuccessfully(createResponse);
    }

    @DisplayName("Creating new user without password path")
    @Test
    public void registerWithoutPasswordTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userDontCreated = check.createdUnsuccessfully(createResponse);
    }

    @DisplayName("Creating new user without name path")
    @Test
    public void registerWithoutNameTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        userDontCreated = check.createdUnsuccessfully(createResponse);
    }
}