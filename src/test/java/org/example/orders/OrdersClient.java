package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.Client;

import java.util.List;
import java.util.Map;

public class OrdersClient extends Client {

    @Step("Creat order")
    public ValidatableResponse creatOrder(String userCreated, List<String> ingredients) {
        return spec()
                .header("authorization", userCreated)
                .body(Map.of("ingredients", ingredients))
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Creat order without authorization")
    public ValidatableResponse creatOrderWithoutAuth(List<String> ingredients) {
        return spec()
                .body(Map.of("ingredients", ingredients))
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Creat order without ingredient")
    public ValidatableResponse creatOrderWithoutIngr(String userCreated) {
        return spec()
                .header("authorization", userCreated)
                .body(Map.of("ingredients", null))
                .when()
                .post("/orders")
                .then().log().all();
    }
}
