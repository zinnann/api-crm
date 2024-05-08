package ru.grak.crm.LoginOnServer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.grak.crm.common.LoginRequest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginOnServer {

    private static final String BASE_URL = "http://localhost:8083";
    private static String jwtToken;
    private static Response response;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;


        response = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .when()
                .post("/api/auth/login");

        assertEquals(200, response.getStatusCode(), "Failed to authenticate");

        jwtToken = response.then().extract().path("token");
        assert jwtToken != null && !jwtToken.isEmpty();
    }

    @Test
    @DisplayName("Авторизация админа")
    public void testAdminLogin() {
        System.out.println("HTTP код: " + response.getStatusCode());
        System.out.println("Тело ответа:");
        System.out.println(response.getBody().asString());
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void testUserLogin() {
        Response userResponse = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("79211331626", "79211331626"))
                .when()
                .post("/api/auth/login");

        assertEquals(200, userResponse.getStatusCode(), "Failed to authenticate user");

        String userToken = userResponse.then().extract().path("token");
        assert userToken != null && !userToken.isEmpty();

        System.out.println("HTTP код: " + userResponse.getStatusCode());
        System.out.println("Тело ответа:");
        System.out.println(userResponse.getBody().asString());
    }
}
