import api.client.OrderClient;
import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DataGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class OrderListTest {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    User user;
    String accessToken;
    Order order;
    List<String> ingredients = new ArrayList<>();
    String ingredient = "61c0c5a71d1f82001bdaaa79";

    @Before
    public void setUp() {
        user = DataGenerator.getRandomUser();
        ValidatableResponse createResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
        order = new Order(ingredients);
        ingredients.add(ingredient);
    }

    @Test
    @DisplayName("Check Orders list has not received by Guest")
    public void checkOrdersListReceivedByGuest() {
        ValidatableResponse validatableResponse = orderClient.receiveListOfOrdersAsGuest();
        validatableResponse.assertThat().statusCode(401);
        validatableResponse.assertThat().body("success", equalTo(false));
    }

    @Test
    @DisplayName("Check Orders list has received by user")
    public void checkOrdersListReceivedByUser() {
        ValidatableResponse validatableResponse = orderClient.receiveListOfOrdersAsUser(user);
        validatableResponse.assertThat().body("success", equalTo(true));
        validatableResponse.assertThat().statusCode(200);
    }

    @After
    public void setDown(){
        userClient.userDeleting(user);
    }
}