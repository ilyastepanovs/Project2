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

public class UserDataChangingTest {
    UserClient userClient = new UserClient();
    User user;
    String accessToken;
    ValidatableResponse loginResponse;

    @Before
    public void setUp() {
        user = DataGenerator.getRandomUser();
        ValidatableResponse createResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
    }

    @Test
    @DisplayName("Check Data Changing as User")
    public void checkDataChangingAsUser() {
        loginResponse = userClient.userLogin(user);
        ValidatableResponse changingResponse = userClient.changingUserDataAsUser(user);
        changingResponse.assertThat().statusCode(200);
        boolean result = loginResponse.extract().path("success");
        assertThat(result, equalTo(true));
    }

    @Test
    @DisplayName("Check Data Changing as Guest")
    public void checkDataChangingAsGuest() {
        ValidatableResponse changingResponse = userClient.changingUserDataAsGuest(user);
        changingResponse.assertThat().statusCode(401);
        String result = changingResponse.extract().path("message");
        assertThat(result, equalTo("You should be authorised"));
    }



    @After
    public void setDown(){
        userClient.userDeleting(user);
    }
}