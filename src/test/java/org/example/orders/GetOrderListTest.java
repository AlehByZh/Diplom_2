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

public class GetOrderListTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private final OrderChecks orderCheck = new OrderChecks();
    private final OrdersClient order = new OrdersClient();
    String userCreated;
    List<String> ingredients1 = Arrays.asList("61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f");
    List<String> ingredients2 = Arrays.asList("61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa75");

    @Step("Delete user")
    @After
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @DisplayName("Get user order list")
    @Test
    public void getUserOrderList() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().fullName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse creatOrderResponse = order.creatOrder(userCreated, ingredients1);
        orderCheck.orderCreated(creatOrderResponse);
        ValidatableResponse creatOrderResponse2 = order.creatOrder(userCreated, ingredients2);
        orderCheck.orderCreated(creatOrderResponse2);

        /*
        Данная проверка упадет, так как считаю что тут неверно отрабатывает Total
        считаю что в нашем тесте для нового пользователя Total
        должен быть равен количеству созданных в тесте заказов
         */
        ValidatableResponse getOrderResponse = order.getOrder(userCreated);
        orderCheck.userOrders(getOrderResponse);
    }

    @DisplayName("Get order list without authorization")
    @Test
    public void getOrderListWithoutAuthorization() {
        ValidatableResponse getOrderResponse = order.getOrderWithoutAuth();
        orderCheck.dontShoworders(getOrderResponse);
    }
}
