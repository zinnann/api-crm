package ru.grak.crm.AddingSubscriber;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.grak.crm.common.LoginRequest;

import static io.restassured.RestAssured.given;

public class AbonentAlreadyAdded {

    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8083";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .when()
                .post("/api/auth/login");

        jwtToken = response.then().extract().path("token");

        response.then().statusCode(200);
    }

    @Test
    @DisplayName("Добавление уже существующего абонента")
    public void testAbonentAlreadyAdded() {
        AddSubscriberRequest addSubscriberRequest = new AddSubscriberRequest();
        addSubscriberRequest.setMsisdn("79211331011");
        addSubscriberRequest.setTariffId("11");
        addSubscriberRequest.setMoney(100.0f);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(addSubscriberRequest)
                .when()
                .post("/api/manager/save")
                .then()
                .extract()
                .response();

        System.out.println("HTTP код: " + response.getStatusCode());
        System.out.println("Тело ответа:");
        System.out.println(response.getBody().asString());
    }
}


