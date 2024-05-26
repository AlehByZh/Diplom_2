package org.example.orders;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserChecks;
import org.example.user.UserClient;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OrderCreatingTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderChecks orderCheck = new OrderChecks();
    private final OrdersClient order = new OrdersClient();
    String userCreated;
    List<String> ingredients = Arrays.asList("61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f");
    List<String> wrongIngredients = Arrays.asList("61c0c5a71d1f82001bdaaa6", "61c0c5a71d1f82001bdaaa5");
    private boolean orderCreated;

    @After
    @Step("Delete user")
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Creating order positive path")
    public void createOrderPositive() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse creatOrderResponse = order.creatOrder(userCreated, ingredients);
        orderCreated = orderCheck.orderCreated(creatOrderResponse);
    }

    @Test
    @DisplayName("Creating order without ingredients")
    public void createOrderWithoutIngredients() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse creatOrderResponse = order.creatOrderWithoutIngr(userCreated);
        orderCheck.orderDontCreated(creatOrderResponse);
    }

    @Test
    @DisplayName("Creating order with wrong hash ingredient")
    public void createOrderWithWrongHash() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().firstName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse creatOrderResponse = order.creatOrder(userCreated, wrongIngredients);
        orderCheck.eternalError(creatOrderResponse);
    }

    @Test
    @DisplayName("Creating order without authorization")
    public void createOrderWithoutAuthorization() {
        ValidatableResponse creatOrderResponse = order.creatOrderWithoutAuth(ingredients);
        orderCheck.orderCreated(creatOrderResponse);
    }
}
