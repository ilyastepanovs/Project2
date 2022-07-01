package utils;
import model.User;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.HashMap;

public class DataGenerator {
    public static User getRandomUser() {
        String userEmail = RandomStringUtils.randomAlphabetic(8)+"@yandex.ru";
        String userPassword = RandomStringUtils.randomAlphabetic(8);
        String userFirstName = RandomStringUtils.randomAlphabetic(8);
        return new User(userEmail, userPassword, userFirstName, "");
    }

    public static HashMap<String,String> generateResponseBodyForUserPostRequest(User user) {
        String userFirstName = user.getFirstName();
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        HashMap<String,String> body = new HashMap<String,String>();
        body.put("email", userEmail);
        body.put("password", userPassword);
        body.put("name", userFirstName);
        return body;
    }
}
