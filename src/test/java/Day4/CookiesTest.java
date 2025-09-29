package Day4;

import io.restassured.http.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CookiesTest {

    @Test
    void testCookiesInResponse(){

        Response response=given()

        .when()
                .get("https://www.google.com/")
        .then()
                .statusCode(200)
                .log().cookies()
                .cookie("AEC",notNullValue())
                .extract().response();

        //Extract a specific cookie
        String cookieValue=response.getCookie("AEC");
        System.out.println("Value of 'AEC' Cookie: "+cookieValue);

        //Extract all the cookies
        Map<String, String> allCookies=response.getCookies();
        System.out.println("All the cookies: "+allCookies);

        //Printing cookies and their values using loop
        for (String key:allCookies.keySet()) {
            System.out.println(key + allCookies.get(key));
        }

        //Get detailed info about the cookie
        Cookie cookie_info=response.getDetailedCookie("AEC");

        System.out.println(cookie_info.hasExpiryDate());
        System.out.println(cookie_info.getExpiryDate());
        System.out.println(cookie_info.hasValue());
        System.out.println(cookie_info.getValue());
        System.out.println(cookie_info.isSecured());
    }
}