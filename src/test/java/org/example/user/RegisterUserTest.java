package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class RegisterUserTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userCreated;

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
}