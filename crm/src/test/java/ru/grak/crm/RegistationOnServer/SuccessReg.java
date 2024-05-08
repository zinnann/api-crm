package ru.grak.crm.RegistationOnServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuccessReg {

    private static final String BASE_URL = "http://localhost:8083";

    @Test
    @DisplayName("Создание нового пользователя")
    public void testRegistration() {
        RestAssured.baseURI = BASE_URL;

        String requestBody = "{\"username\": \"79813431626\", \"password\": \"79813431626\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/auth/registration");

        int statusCode = response.getStatusCode();
        assertTrue(statusCode == 200 || statusCode == 201, "Failed to register");

        System.out.println("HTTP код: " + response.getStatusCode());
        System.out.println("Тело ответа:");
        System.out.println(response.getBody().asString());
    }
}



