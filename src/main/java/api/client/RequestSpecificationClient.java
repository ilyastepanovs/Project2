package api.client;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationClient {

    protected static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    public RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-type", "application/json")
                .setBaseUri(BASE_URI)
                .build();
    }
}
