package info.jab.ms.at.controller;

import info.jab.ms.at.AcceptanceTestBaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class GodControllerAT extends AcceptanceTestBaseTest {

    @Test
    void shouldReturnExpectedGodSumFromAllMythologies() {
        given()
        .when()
            .get("/api/v1/gods/sum")
        .then()
            .statusCode(200)
            .body(equalTo("78179288397447443426"));
    }
}
