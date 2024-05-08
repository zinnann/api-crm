package ru.grak.crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class CrmApplicationTests {

    @Test
    void successRegTest() {
        System.out.println("Hello");
    }

}
