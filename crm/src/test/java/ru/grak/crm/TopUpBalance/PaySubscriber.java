package ru.grak.crm.TopUpBalance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

public class PaySubscriber {

    private static final String BASE_URL = "http://localhost:8083";
    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;

        Response authResponse = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"79211331626\", \"password\": \"79211331626\"}")
                .when()
                .post("/api/auth/login");

        jwtToken = authResponse.then().extract().path("token");

        assertEquals(200, authResponse.getStatusCode(), "Failed to authenticate");
    }

    @Test
    @DisplayName("Пополнение баланса абонента")
    public void testPaySubscriber() {
        String requestBody = "{\"msisdn\": \"79211331626\", \"money\": 50.0}";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(requestBody)
                .when()
                .patch("/api/subscriber/pay");

        assertEquals(200, response.getStatusCode(), "Failed to execute PATCH request");

        float balance = Float.parseFloat(response.getBody().asString());

        System.out.println(balance);
    }
}

