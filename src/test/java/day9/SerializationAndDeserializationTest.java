package day9;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.*;
import io.restassured.response.*;


public class SerializationAndDeserializationTest {

    String stuId; // premenovane na stu

    @Test
    public void testSerialization(){

        String[] courses= {"Selenium", "Java", "Python"};
        Student stu=new Student("John","Delhi","1234567890",courses); // prehodene na stu

        stuId=
        given()
                .contentType("application/json")
                .body(stu) // serialization happens
        .when()
                .post("http://localhost:3000/students")
        .then()
                .statusCode(201)
                .log().body()
        .extract().response().jsonPath().getString("id");
    }

    @Test(dependsOnMethods="testSerialization")
    public void testDeserialization() {

        Response response=given()
                .pathParam("id", stuId)
                .when()
                .get("http://localhost:3000/students/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        //Extract "id" separately from JSON response
        String extractedId=response.jsonPath().getString("id");

        //Deserialization - convert JSON to Student Object
        Student stu=response.as(Student.class);

        assertThat(stu.getName(), is("John"));
        assertThat(stu.getLocation(), is("Delhi"));
        assertThat(stu.getPhone(), is("1234567890"));
        assertThat(stu.getCourses()[0], is("Selenium"));

        //Printing student object details
        System.out.println("Student details: "+stu +extractedId);
    }

    @Test(dependsOnMethods = "testDeserialization")
    public void deleteStudent(){

        given()
                .pathParam("id", stuId)
        .when()
                .delete("http://localhost:3000/students/{id}")
        .then()
                .statusCode(200);
    }
}