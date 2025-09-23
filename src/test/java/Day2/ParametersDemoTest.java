package Day2;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ParametersDemoTest {

    @Test
    void pathParams(){
        given()
            .pathParam("country", "India") // path parameter
        .when()
            .get("https://restcountries.com/v2/name/{country}")
        .then()
            .statusCode(200)
            .log().body();
    }

    @Test
    void queryParams(){
        given()
            .header("x-api-key", "reqres-free-v1")
                .queryParam("page", 2)
                .queryParam("id", 5)
        .when()
            .get("https://reqres.in/api/users")
        .then()
            .statusCode(200)
            .log().body();
    }
}