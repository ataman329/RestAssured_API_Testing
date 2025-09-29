package Day6;

import io.restassured.path.json.*;
import io.restassured.response.*;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
// matchers su specialne nadizajnovane na API testing. vraj

public class ParsingJSONArrayTest {
    @Test
    void testJsonResponseBody(){

        ResponseBody responseBody = given()
                .when()
                    .get("http://localhost:3000/employees")
                .then()
                    .statusCode(200)
                    .extract().response().body();
//ak je odpoved vo formate JSON object, pouzijeme statement toString.
//ak je odpoved vo formate response body, vtedy pouzijeme statement asString
        JsonPath jsonpath=new JsonPath(responseBody.asString()); // toto konvertuje cely JSON array do JSON path

        //get the size of JSON array
        int employeeCount=jsonpath.getInt("size()");

        //print all the details of the employee
        for (int i = 0; i < employeeCount; i++) {
            String firstName=jsonpath.getString("["+i+"].first_name");
            String lastName=jsonpath.getString("["+i+"].last_name");
            String email=jsonpath.getString("["+i+"].email");
            String gender=jsonpath.getString("["+i+"].gender");

            System.out.println(firstName+"   "+lastName+"  "+email+"   "+gender);
        }

        //Search for an employee named "Steve" in the list
        boolean status=false;

        for (int i = 0; i < employeeCount; i++) {
            String firstName=jsonpath.getString("["+i+"].first_name");
            if (firstName.equals("Steve")){
                status=true;
                break; // ak loopa v zozname nenajde "Steve" automaticky sa ukonci a nepojde do infinito
            }
        }
        assertThat(status, is(true)); // Steven exists in the list, or not
    }
}