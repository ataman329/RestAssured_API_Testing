package Day3;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationTests {

    //1. Basic authentication
    @Test
    void verifyBasicAuth(){
                given()
                        .auth().basic("postman", "password")
                .when()
                    .get("https://postman-echo.com/basic-auth")
                .then()
                    .statusCode(200)
                    .body("authenticated", equalTo(true))
                    .log().body();
    }

    //2. Basic preemptive Authentication
    @Test
    void verifyPreemptiveAuth(){
        given()
                .auth().preemptive().basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().body();
    }
}