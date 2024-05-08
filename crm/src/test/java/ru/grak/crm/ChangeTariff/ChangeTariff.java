package ru.grak.crm.ChangeTariff;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.grak.crm.common.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeTariff {

    private static final String BASE_URL = "http://localhost:8083";
    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .when()
                .post("/api/auth/login");

        jwtToken = response.then().extract().path("token");

        assertEquals(200, response.getStatusCode(), "Failed to authenticate");
    }

    @Test
    @DisplayName("PATCH запрос на смену тарифа")
    public void testPatchRequest() {
        String requestBody = "{\"msisdn\": \"79211331626\", \"tariffId\": \"12\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(requestBody)
                .when()
                .patch("/api/manager/change-tariff");

        assertEquals(200, response.getStatusCode(), "Failed to execute PATCH request");

        String responseBody = response.getBody().asString();

        System.out.println("HTTP код: " + response.getStatusCode());
        System.out.println("Тело ответа:");
        System.out.println(responseBody);
    }
}

