package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

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
}
