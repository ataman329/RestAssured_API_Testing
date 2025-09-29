package Day7;

import io.restassured.path.xml.*;
import org.testng.annotations.*;
import io.restassured.response.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ParsingXMLTest {

    /**
     * Test case to validate XML response data
     * Validates:
     * - HTTP status code
     * - Content type
     * - Specific XML element values '@' notation
     */

    @Test(priority = 1)
    void testXMLResponse() {
        given()
        .when()
                .get("https://mocktarget.apigee.net/xml")
        .then()
                .statusCode(200)
                .contentType("application/xml")
                .header("Content-type", "application/xml; charset=utf-8")
                .header("Content-type", equalTo("application/xml; charset=utf-8"))
                .body("root.city", equalTo("San Jose"))
                .body("root.firstName", equalTo("John"))
                .body("root.lastName", equalTo("Doe"))
                .body("root.state", equalTo("CA"))
                .log().body();
    }

    @Test(priority = 2)
    void testXMLResponse2() {
        given()
        .when()
                .get("https://httpbin.org/xml")
        .then()
                .statusCode(200)
                .contentType("application/xml")
                .body("slideshow.@title",equalTo("Sample Slide Show"))
                .body("slideshow.@date",equalTo("Date of publication"))
                .body("slideshow.@author",equalTo("Yours Truly"))
                .log().body();
    }

    @Test(priority = 3)
    void testParsingXMLResponse() {
        Response response=given()
                .when()
                .get("https://httpbin.org/xml")
                .then()
                .statusCode(200)
                .contentType("application/xml")
                .extract().response();


        XmlPath xmlPath=new XmlPath(response.asString());

        //number of slides in the response

        List<String> slideTitles=xmlPath.getList("slideshow.slide.title");

        //counting slides
        assertThat(slideTitles.size(), is (2));

        //validate slide titles
        assertThat(slideTitles.get(0),is("Wake up to WonderWidgets!"));
        assertThat(slideTitles.get(1),is("Overview"));
        assertThat(slideTitles,hasItems("Wake up to WonderWidgets!","Overview")); // multiple titles

        // number of items
        List<String> items=xmlPath.getList("slideshow.slide.item");
        System.out.println("Number of items: "+items.size());
        assertThat(items.size(), is(3));

        //validate items data
        assertThat(items.get(0), is("WonderWidgets"));
        assertThat(items.get(2), is("buys"));

        assertThat(items,hasItems("WonderWidgets","buys")); // multiple items

        //check presence of item in the response

        boolean status=false;
        for (String item : items) {
            if(item.equals("WonderWidgets")){
                status=true;
                break;
            }
        }
        assertThat(status, is(true));
    }
}