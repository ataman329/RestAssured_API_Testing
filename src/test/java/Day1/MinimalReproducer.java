import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test
void main() {
    int code =
            given().log().all()
                    .when().get("https://reqres.in/api/users?page=2")
                    .then().log().all()
                    .extract().statusCode();

    System.out.println("STATUS = " + code);
}