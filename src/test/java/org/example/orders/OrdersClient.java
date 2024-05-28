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
                .body(Map.of("ingredients",""))
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Get user order list")
    public ValidatableResponse getOrder(String userCreated) {
        return spec()
                .header("authorization", userCreated)
                .when()
                .get("/orders")
                .then().log().all();
    }

    @Step("Get order list without authorization")
    public ValidatableResponse getOrderWithoutAuth() {
        return spec()
                .when()
                .get("/orders")
                .then().log().all();
    }

    @Step("Get list of ingredients")
    public ValidatableResponse getListOfIngredients() {
        return spec()
                .when()
                .get("/ingredients")
                .then().log().all();
    }
}
