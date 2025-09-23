package Day2;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Post request
 * -----
 * Request payload/Body ( JSON format)
 * Post URL
 * Response
 *
 * Different ways to create post request body/ request payload.
 * ---------------
 * 1) HashMap
 * 2) org.json library
 * 3) Using java POJO class
 * 4) External json file
 */

public class PostRequestExamplesTest {

    String studentId;

//1. Create post request body using HashMap
    //@Test
    void createStudentUsingHashMap(){

        HashMap <String, Object> requestBody=new HashMap<>();

        requestBody.put("name", "Scott");
        requestBody.put("location", "France");
        requestBody.put("phone", "0609 112 777");

        String courses[]= {"C", "C++"};
        requestBody.put("courses",courses);

        studentId=given()
                .contentType("application/json")
                .body(requestBody)
        .when()
                .post("http://localhost:3000/students")
        .then()
                .statusCode(201)
                .body("name", equalTo("Scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("0609 112 777"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //2. Create POST request body using json library
    //@Test
    void createStudentUsingJsonLibrary(){

        JSONObject requestBody=new JSONObject();
        requestBody.put("name", "Scott");
        requestBody.put("location", "France");
        requestBody.put("phone", "0609 112 777");

        String courses[]= {"C", "C++"};
        requestBody.put("courses",courses);

        studentId=given()
                .contentType("application/json")
                .body(requestBody.toString())
            .when()
                .post("http://localhost:3000/students")
            .then()
                .statusCode(201)
                .body("name", equalTo("Scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("0609 112 777"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //3. Create POST request body using POJO class
    //@Test
    void createStudentUsingPojoClass(){

        StudentPojo requestBody=new StudentPojo();
        requestBody.setName("Scott");
        requestBody.setLocation("France");
        requestBody.setPhone("0609 112 777");

        String courses[]= {"C", "C++"};
        requestBody.setCourses(courses);

        studentId=given()
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("http://localhost:3000/students")
            .then()
                .statusCode(201)
                .body("name", equalTo(requestBody.getName()))
                .body("location", equalTo(requestBody.getLocation()))
                .body("phone", equalTo(requestBody.getPhone()))
                .body("courses[0]", equalTo(requestBody.getCourses()[0]))
                .body("courses[1]", equalTo(requestBody.getCourses()[1]))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    //4. Create POST request body using external JSON File
    @Test
    void createStudentUsingExternalFile() throws FileNotFoundException {
        File myfile=new File(".\\src\\test\\java\\day2\\body.json");
        FileReader fileReader=new FileReader(myfile);
        JSONTokener jsonTokener=new JSONTokener(fileReader);
        JSONObject requestBody=new JSONObject(jsonTokener);

        studentId=given()
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post("http://localhost:3000/students")
                .then()
                .statusCode(201)
                .body("name", equalTo("Scott"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("0609 112 777"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json")
                .log().body()
                .extract().jsonPath().getString("id");
    }

    @AfterMethod
    void deleteStudentRecord(){
        given()

                .when()
                .delete("http://localhost:3000/students/"+studentId)
                .then()
                .statusCode(200);
    }
}