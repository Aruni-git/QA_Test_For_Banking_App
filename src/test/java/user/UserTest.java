package user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;

public class UserTest extends BaseTest {

    public static String userId = "";
    public static String accountId;
    Response response;
    ResponseBody body;
    RequestSpecification httpRequest;
    JSONObject requestParams = new JSONObject();

    @BeforeTest
    public void setUp() {
        navURL();
    }

    @Test(priority = 1)
    public void createUser() {

        httpRequest = RestAssured.given();

        requestParams.put("first_name", "Victoria");
        requestParams.put("last_name", "Denis");
        httpRequest.body(requestParams.toJSONString());
        httpRequest.contentType("application/json");
        response = httpRequest.post("/user/create_user");
        body = response.getBody();

        Assert.assertEquals(response.getStatusCode(), 200);

        String userId = body.jsonPath().getString("user_id");
        UserTest.userId = userId;
        System.out.println(userId);
    }

    @Test(priority = 2)
    public void createAccount() {

        httpRequest = RestAssured.given();

        requestParams.put("user_id", userId);
        httpRequest.body(requestParams.toJSONString());
        httpRequest.contentType("application/json");

        response = httpRequest.post("/account/create_account");
        body = response.getBody();

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        String account_Id = body.jsonPath().getString("account_id");
        UserTest.accountId = account_Id;
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void deposit() {

        httpRequest = RestAssured.given().queryParam(UserTest.accountId).basePath("/account/{accountId}/deposit");
        requestParams.put("amount", "10000");
        httpRequest.body(requestParams.toJSONString());
        httpRequest.contentType("application/json");

        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        String account_Id = body.jsonPath().getString("account_id");
        UserTest.accountId = account_Id;

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 4)
    public void withdraw() {

        httpRequest = RestAssured.given().queryParam(UserTest.accountId).basePath("/account/{accountId}/withdraw");

        requestParams.put("amount", "50");
        httpRequest.body(requestParams.toJSONString());
        httpRequest.contentType("application/json");

        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        String account_Id = body.jsonPath().getString("account_id");
        UserTest.accountId = account_Id;

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 5)
    public void send() {

        String destAccountId = "3f64d44b-cc5c-44e3-9f2a-c8481d99f8cc";
        httpRequest = RestAssured.given().queryParam(UserTest.accountId, destAccountId).basePath("/account/{srcAccountId}/send/{destAccountId}");

        requestParams.put("amount", "100");
        httpRequest.body(requestParams.toJSONString());
        httpRequest.contentType("application/json");

        body = response.getBody();

        System.out.println(response.getStatusLine());
        System.out.println(body.asString());

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 6)
    public void GetAccountBalance() {
        httpRequest = RestAssured.given();

        response = httpRequest.get("/account/3f64d44b-cc5c-44e3-9f2a-c8481d99f8cc/balance");

        System.out.println("Response Body is =>  " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}

