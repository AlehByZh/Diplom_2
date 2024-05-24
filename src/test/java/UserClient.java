import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    private static final String AUTH_PATH = "/api/auth";

    public ValidatableResponse deleteUser(String created) {
        return given().log().all()
                .header("authorization", created)
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .delete(AUTH_PATH + "/user")
                .then().log().all();
    }

    public ValidatableResponse creatUser(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(AUTH_PATH + "/register")
                .then().log().all();
    }
}
