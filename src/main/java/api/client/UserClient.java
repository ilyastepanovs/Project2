package api.client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.*;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static utils.DataGenerator.generateResponseBodyForUserPostRequest;

public class UserClient extends RequestSpecificationClient {
    static final String PATH = "/api/auth";

    @Step("User login")
    public ValidatableResponse userLogin(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(PATH + "/login")
                .then();
    }

    @Step("User registration")
    public ValidatableResponse userRegistration(User user){
        HashMap<String,String> body = generateResponseBodyForUserPostRequest(user);
        return given()
                .spec(getBaseSpec())
                .body(body)
                .when()
                .post(PATH + "/register")
                .then();
    }

    @Step("User deleting")
    public void userDeleting(User user){
        given()
                .spec(getBaseSpec())
                .header("Authorization", user.getAccessToken())
                .when()
                .delete(PATH + "/user")
                .then();
    }

    @Step("Changing User Data As Guest")
    public ValidatableResponse changingUserDataAsGuest(User user){
        HashMap<String,String> body = generateResponseBodyForUserPostRequest(user);
        return given()
                .spec(getBaseSpec())
                .body(body)
                .when()
                .patch(PATH +  "/user")
                .then();
    }

    @Step("Changing User Data As User")
    public ValidatableResponse changingUserDataAsUser(User user){
        HashMap<String,String> body = generateResponseBodyForUserPostRequest(user);
        return given()
                .spec(getBaseSpec())
                .header("Authorization", user.getAccessToken())
                .body(body)
                .when()
                .patch(PATH +  "/user")
                .then();
    }
}
