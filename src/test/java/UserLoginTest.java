import api.client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.DataGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {
    UserClient userClient = new UserClient();
    User user;
    String accessToken;

    @Before
    public void setUp() {
        user = DataGenerator.getRandomUser();
        ValidatableResponse createResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
    }

    @Test
    @DisplayName("Check Login With Valid Creds")
    public void checkLoginWithValidCreds() {
        ValidatableResponse loginResponse = userClient.userLogin(user);
        boolean result = loginResponse.extract().path("success");
        loginResponse.assertThat().statusCode(200);
        assertThat(result, equalTo(true));
    }

    @Test
    @DisplayName("Check Login With Invalid Creds")
    public void checkLoginWithInvalidCreds() {
        ValidatableResponse loginResponse = userClient.userLogin(new User("1any@mail.ru","any123"));
        boolean result = loginResponse.extract().path("success");
        loginResponse.assertThat().statusCode(401);
        assertThat(result, equalTo(false));
    }

    @After
    public void setDown(){
        if( accessToken != null){
            userClient.userDeleting(user);
        }
    }
}