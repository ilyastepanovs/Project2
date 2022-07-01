package api.client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.User;

import static io.restassured.RestAssured.given;

public class OrderClient extends RequestSpecificationClient {
    private static final String PATH = "/api/orders";

    @Step("Create Order As Guest")
    public ValidatableResponse createOrderAsGuest(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Create Order As User")
    public ValidatableResponse createOrderAsUser(Order order, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", user.getAccessToken())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Receive List of Orders As Guest")
    public ValidatableResponse receiveListOfOrdersAsGuest() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(PATH)
                .then();
    }

    @Step("Receive List of Orders As User")
    public ValidatableResponse receiveListOfOrdersAsUser(User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", user.getAccessToken())
                .when()
                .get(PATH)
                .then();
    }
}
