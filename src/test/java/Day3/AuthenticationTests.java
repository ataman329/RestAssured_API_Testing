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

    //3. Digest Authentication
    @Test
    void verifyDigestAuth(){
        given()
                .auth().digest("postman", "password")
        .when()
                .get("https://postman-echo.com/basic-auth")
        .then()
                .statusCode(200)
                .body("authenticated", equalTo(true))
                .log().body();
    }

    //4. Bearer token Authentication
    @Test
    void verifyTokenAuth(){

        String bearerToken = System.getenv("GITHUB_TOKEN");

        given()
                .header("Authorization","Bearer "+bearerToken)
        .when()
                .get("https://api.github.com/user/repos")
        .then()
                .statusCode(200)
                .log().body();
    }

    //5. API Key Authentication
    @Test
    void verifyAPIkeyAuth(){

        String APIkey="fe9c5cddb7e01d747b4611c3fc9eaf2c";

        given()
                .queryParam("q", "Delhi")
                .queryParam("appid",APIkey)
        .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
        .then()
                .statusCode(200)
                .log().body();
    }
}