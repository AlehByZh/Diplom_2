package org.example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UpdateUserDataTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    String userCreated;
    String userUpdatedData;

    @Step("Delete user")
    @After
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @DisplayName("Update user name test")
    @Test
    public void updateUserName() {
        Faker faker = new Faker();
        String newName = faker.name().firstName();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse updateResponse = client.updateNameData(userCreated, newName);
        userUpdatedData = check.updatedNameSuccessfully(updateResponse, newName);
    }

    @DisplayName("Update user email test")
    @Test
    public void updateUserEmail() {
        Faker faker = new Faker();
        String newEmail = faker.internet().emailAddress();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse updateResponse = client.updateEmailData(userCreated, newEmail);
        userUpdatedData = check.updatedEmailSuccessfully(updateResponse, newEmail);
    }

    @DisplayName("Update user data without authorization")
    @Test
    public void updateUserDataWithoutAuthorization() {
        Faker faker = new Faker();
        String newEmail = faker.internet().emailAddress();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse updateResponse = client.updateUnsuccessfullyData(newEmail);
        check.updatedDataUnsuccessfully(updateResponse);
    }
}
