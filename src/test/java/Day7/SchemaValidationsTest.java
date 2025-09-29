package Day7;

import static io.restassured.RestAssured.*;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.*;

public class SchemaValidationsTest {

    //JsonSchema validation
    @Test(priority = 1)
    void testJsonSchema () {
        given()
        .when()
                .get("https://mocktarget.apigee.net/json")
        .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"));
    }

    //XML schema/XSD validation
    @Test(priority = 2)
    void testXMLSchema () {
        given()
                .when()
                .get("https://mocktarget.apigee.net/xml")
                .then()
                .assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("xmlSchema.xsd"));
    }
}