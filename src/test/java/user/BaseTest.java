package user;

import io.restassured.RestAssured;

public class BaseTest {
    public void navURL() {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = "http://localhost:5000";
    }
}
