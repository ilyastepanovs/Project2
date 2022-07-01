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

public class UserCreationTest {
    UserClient userClient = new UserClient();
    String accessToken;
    User user;

    @Before
    public void setUp(){
        user = DataGenerator.getRandomUser();
    }

    @Test
    @DisplayName("Check User Creation")
    public void checkUserCreation(){
        ValidatableResponse createResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
        createResponse.assertThat().statusCode(200);
        assertThat(createResponse.extract().path("success"),
                equalTo(true));
    }

    @Test
    @DisplayName("Check Duplicate User Creation")
    public void checkDuplicateUserCreation(){
        ValidatableResponse createResponse = userClient.userRegistration(user);
        ValidatableResponse createDuplicateResponse = userClient.userRegistration(user);
        accessToken = createResponse.extract().path("accessToken");
        user.setAccessToken(accessToken);
        createDuplicateResponse.assertThat().statusCode(403);
        assertThat(createDuplicateResponse.extract().path("message"),
                equalTo("User already exists"));
    }

    @Test
    @DisplayName("Check User Creation Without Email")
    public void checkUserCreationWithoutEmail(){
        ValidatableResponse createResponse = userClient.userRegistration(
                new User(null, user.getPassword(), user.getFirstName(), accessToken));
        createResponse.assertThat().statusCode(403);
        assertThat(createResponse.extract().path("message"),
                equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check User Creation Without Password")
    public void checkUserCreationWithoutPassword(){
        ValidatableResponse createResponse = userClient.userRegistration(
                new User(user.getEmail(), null, user.getFirstName(), accessToken));
        createResponse.assertThat().statusCode(403);
        assertThat(createResponse.extract().path("message"),
                equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Check User Creation Without First Name")
    public void checkUserCreationWithoutFirstName(){
        ValidatableResponse createResponse = userClient.userRegistration(
                new User(user.getEmail(), user.getPassword(), null, accessToken));
        createResponse.assertThat().statusCode(403);
        assertThat(createResponse.extract().path("message"),
                equalTo("Email, password and name are required fields"));
    }

    @After
    public void setDown(){
        if( accessToken != null){
            userClient.userDeleting(user);
        }
    }
}