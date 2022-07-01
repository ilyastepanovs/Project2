import api.client.OrderClient;
import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Order;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DataGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest {
    UserClient userClient = new UserClient();
    OrderClient orderClient = new OrderClient();
    User user;
    Order order;
    List<String> ingredients = new ArrayList<>();
    String accessToken;
    String ingredient = "61c0c5a71d1f82001bdaaa79";
    String nonExpectedIngredient = RandomStringUtils.randomAlphabetic(8);

    @Before
    public void setUp() {
        order = new Order(ingredients);
        user = DataGenerator.getRandomUser();
        ValidatableResponse createResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
    }

    @Test
    @DisplayName("Check Place Order As User")
    public void checkPlaceOrderAsUser() {
        ingredients.add(ingredient);
        ValidatableResponse orderCreated = orderClient.createOrderAsUser(order, user);
        boolean result = orderCreated.extract().path("success");
        assertThat(result, equalTo(true));
        orderCreated.assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check Place Order As Guest")
    public void checkPlaceOrderAsGuest() {
        ingredients.add(ingredient);
        ValidatableResponse orderCreated = orderClient.createOrderAsGuest(order);
        boolean result = orderCreated.extract().path("success");
        assertThat(result, equalTo(true));
        orderCreated.assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Check Place Order Without Ingredients")
    public void checkPlacingOrderWithoutIngredients() {
        ValidatableResponse orderCreated = orderClient.createOrderAsGuest(order);
        String result = orderCreated.extract().path("message");
        assertThat(result, equalTo("Ingredient ids must be provided"));
        orderCreated.assertThat().statusCode(400);
    }

    @Test
    @DisplayName("Order With Non Expected Ingredient")
    public void checkPlacingOrderWithNonExpectedIngredient() {
        ingredients.add(nonExpectedIngredient);
        ValidatableResponse orderCreated = orderClient.createOrderAsGuest(order);
        orderCreated.assertThat().statusCode(500);
    }

    @After
    public void setDown(){
        if( accessToken != null){
            userClient.userDeleting(user);
        }
    }
}