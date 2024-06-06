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

    @After
    @Step("Delete user")
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Creating new user positive path")
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

    @Test
    @DisplayName("Creating existing user path")
    public void registerExistingTest() {
        var user = User.generic();
        ValidatableResponse createResponse = client.creatUser(user);
        check.createdUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("Creating new user without email path")
    public void registerWithoutEmailTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        check.createdUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("Creating new user without password path")
    public void registerWithoutPasswordTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        check.createdUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("Creating new user without name path")
    public void registerWithoutNameTest() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
        ValidatableResponse createResponse = client.creatUser(user);
        check.createdUnsuccessfully(createResponse);
    }
}