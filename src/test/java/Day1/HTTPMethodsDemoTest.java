package Day1;

import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HTTPMethodsDemoTest {

    String userId;

    // 1. Test to retrieve users and validate response
    @Test(priority=1)
    void getUsers () {
        given()
                .header("x-api-key", "reqres-free-v1")
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body(containsString("email"))
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(2000L))
                .log().all();
    }

    // 2. Test to create new user and validate the response
    @Test(priority=2)
    void createUser () {

        Map<String, String> data=new HashMap<>();
        data.put("name", "pavan");
        data.put("job", "trainer");

        userId=
        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(data)
                .log().all()
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(2000L))
                .body("name",equalTo("pavan"))
                .body("job",equalTo("trainer"))
                .body("id", not(emptyOrNullString()))
                .body("createdAt", not(emptyOrNullString()))
                .log().ifValidationFails()
                .extract().path("id");
    }


    // 3. Test to update existing user and validate response
    @Test(priority=3, dependsOnMethods = {"createUser"})
    void updateUser () {

        Map<String, String> data=new HashMap<>();
        data.put("name", "pavan");
        data.put("job", "trainer");

        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(data)
                .log().all()
        .when()
                .put("https://reqres.in/api/users/"+userId)
        .then()
                .statusCode(200)
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .time(lessThan(2000L))
                .body("name",equalTo("pavan"))
                .body("job",equalTo("trainer"))
                .body("updatedAt", not(emptyOrNullString()))
                .log().ifValidationFails();
    }

    // 4. Test to delete existing user and validate response
    @Test(priority=4, dependsOnMethods = {"createUser", "updateUser"})
    void deleteUser () {

        given()
                .header("x-api-key", "reqres-free-v1")
        .when()
                .delete("https://reqres.in/api/users/"+userId)
        .then()
                .statusCode(204)
                .time(lessThan(2000L))
                .body(emptyOrNullString())
                .log().all();
    }
}