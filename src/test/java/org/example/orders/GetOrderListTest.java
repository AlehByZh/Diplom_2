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
    List<String> ingredients;


    @After
    @Step("Delete user")
    public void deleteUser() {
        if (userCreated != null && !userCreated.isEmpty()){
            ValidatableResponse deleteResponse = client.deleteUser(userCreated);
            check.deleteSuccessFully(deleteResponse);
        }
    }

    @Test
    @DisplayName("Get user order list")
    public void getUserOrderList() {
        Faker faker = new Faker();
        var user = User.builder()
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .name(faker.name().fullName())
                .build();
        ValidatableResponse creatResponse = client.creatUser(user);
        userCreated = check.createdSuccessfully(creatResponse);

        ValidatableResponse getListIngredients = order.getListOfIngredients();
        List<String> allIngredients = orderCheck.getAllIngredients(getListIngredients);
        ingredients = allIngredients.size() > 2 ? allIngredients.subList(0, 2) : allIngredients;

        ValidatableResponse creatOrderResponse = order.creatOrder(userCreated, ingredients);
        orderCheck.orderCreated(creatOrderResponse);
        ValidatableResponse creatOrderResponse2 = order.creatOrder(userCreated, ingredients);
        orderCheck.orderCreated(creatOrderResponse2);

        /*
        Данная проверка упадет, так как считаю что тут неверно отрабатывает Total
        считаю что в нашем тесте для нового пользователя Total
        должен быть равен количеству созданных в тесте заказов
         */
        ValidatableResponse getOrderResponse = order.getOrder(userCreated);
        orderCheck.userOrders(getOrderResponse);
    }

    @Test
    @DisplayName("Get order list without authorization")
    public void getOrderListWithoutAuthorization() {
        ValidatableResponse getOrderResponse = order.getOrderWithoutAuth();
        orderCheck.dontShoworders(getOrderResponse);
    }
}
