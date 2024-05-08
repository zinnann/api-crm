package ru.grak.crm.AddingSubscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.grak.crm.common.LoginRequest;

import static io.restassured.RestAssured.given;

public class APITest {

    @Getter
    private static String jwtToken;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8083";

        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("admin", "admin"))
                .when()
                .post("/api/auth/login");

        jwtToken = loginResponse.then().extract().path("token");

        loginResponse.then().statusCode(200);
    }


    @Test
    @DisplayName("Добавление нового абонента")
    public void testAPIRequest() throws Exception {
        AddSubscriberRequest addSubscriberRequest = new AddSubscriberRequest();
        addSubscriberRequest.setMsisdn("79011331027");
        addSubscriberRequest.setTariffId("11");
        addSubscriberRequest.setMoney(100.0f);

        Response addSubscriberResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(addSubscriberRequest)
                .when()
                .post("/api/manager/save")
                .then()
                .extract()
                .response();

        System.out.println("HTTP код: " + addSubscriberResponse.getStatusCode());

        String responseBody = addSubscriberResponse.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        SubscriberResponse subscriberResponse = mapper.readValue(responseBody, SubscriberResponse.class);

        System.out.println("Тело ответа:");
        System.out.println("id: " + subscriberResponse.getId());
        System.out.println("phoneNumber: " + subscriberResponse.getPhoneNumber());
        System.out.println("tariff: " + subscriberResponse.getTariff());
        System.out.println("balance: " + subscriberResponse.getBalance());

        addSubscriberResponse.then().statusCode(200);
    }
}
