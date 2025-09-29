package Day4;

import io.restassured.http.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HeadersTest {

    @Test
    void testCookiesInResponse(){

        Response response=given()

        .when()
                .get("https://www.google.com/")
        .then()
                .log().headers()
                .statusCode(200)
                .header("Content-Type", containsString("text/html"))
                .header("Content-Encoding", notNullValue())
                .header("Content-Encoding", equalTo("gzip"))
                .header("X-Frame-Options", equalTo("SAMEORIGIN"))
                .header("Server", equalTo ("gws"))
                .extract().response();

        //Extract specific header information
        String headerValue=response.getHeader("Content-Type");
        System.out.println("Value of header (Content-Type):"+ headerValue);
        
        //Extract all the headers and print them
        Headers headers=response.getHeaders();

        for (Header h : headers) {
            System.out.println(h.getName() + " : "+ h.getValue());
        }
    }
}