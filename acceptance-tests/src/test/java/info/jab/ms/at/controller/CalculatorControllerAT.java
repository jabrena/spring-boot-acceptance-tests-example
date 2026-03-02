package info.jab.ms.at.controller;

import info.jab.ms.at.AcceptanceTestBaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CalculatorControllerAT extends AcceptanceTestBaseTest {

    @ParameterizedTest(name = "sum({0}, {1}) = {2}")
    @CsvSource({
        " 3,   5,  8",
        "-3,  10,  7",
        " 0,   0,  0",
        "-5,  -5, -10"
    })
    void shouldReturnSumOfTwoIntegers(int a, int b, int expected) {
        given()
            .contentType(ContentType.JSON)
            .body(String.format("{\"a\": %d, \"b\": %d}", a, b))
        .when()
            .post("/api/v1/sum")
        .then()
            .statusCode(200)
            .body(equalTo(String.valueOf(expected)));
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
