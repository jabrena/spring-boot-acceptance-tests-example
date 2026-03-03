package info.jab.ms.at.controller;

import info.jab.ms.at.AcceptanceTestBaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CalculatorControllerAT extends AcceptanceTestBaseTest {

    @Test
    void shouldReturnSumOfTwoIntegers() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"a\": 1, \"b\": 1}")
        .when()
            .post("/api/v1/sum")
        .then()
            .statusCode(200)
            .body(equalTo("2"));
    }

    @Test
    void shouldReturnBadRequestWhenBodyIsMissing() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"a\": 3}")
        .when()
            .post("/api/v1/sum")
        .then()
            .statusCode(400);
    }
}
