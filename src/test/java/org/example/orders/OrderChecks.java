package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class OrderChecks {

    @Step("Check order is created")
    public boolean orderCreated(ValidatableResponse creatOrderResponse) {
        boolean orderCreated = creatOrderResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("success");
        assertTrue(orderCreated);
        return orderCreated;
    }

    @Step("Check order don't created")
    public boolean orderDontCreated(ValidatableResponse creatOrderResponse) {
        boolean orderDontCreated = creatOrderResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .path("success");
        assertFalse(orderDontCreated);
        return orderDontCreated;
    }

    @Step("Check is order don't created and error massage")
    public String eternalError(ValidatableResponse creatOrderResponse) {
        String eternalError = creatOrderResponse
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR)
                .extract()
                .response().getBody().asString();
        assertTrue(eternalError.contains("<pre>Internal Server Error</pre>"));
        return eternalError;
    }

    @Step("check user number of orders")
    public int userOrders(ValidatableResponse getOrderResponse) {
        int totalOrders = getOrderResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("total");
        assertEquals(2,totalOrders);
        return totalOrders;
    }

    @Step("Check is order list is no show")
    public boolean dontShoworders(ValidatableResponse getOrderResponse) {
        boolean noOrders = getOrderResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
        assertFalse(noOrders);
        return noOrders;
    }

    @Step("Check is ingredient list in not empty")
    public List<String> getAllIngredients(ValidatableResponse getIngredientsResponse) {
        List<String> allIngredients = getIngredientsResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("data._id");
        assertFalse(allIngredients.isEmpty());
        return allIngredients;
    }
}
