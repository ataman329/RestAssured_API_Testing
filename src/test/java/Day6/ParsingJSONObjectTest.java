package Day6;

import io.restassured.path.json.*;
import io.restassured.response.*;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ParsingJSONObjectTest {

    @Test
    void testJsonResponseBody(){

        ResponseBody responseBody = given()
        .when()
                .get("http://localhost:3000/store")
        .then()
                .statusCode(200)
                .extract().response().body();

//ak je odpoved vo formate JSON object, pouzijeme statement toString.
//ak je odpoved vo formate response body, vtedy pouzijeme statement asString
        JsonPath jsonpath=new JsonPath(responseBody.asString()); // toto konvertuje cely JSON array do JSON path

        //get the size of JSON array
        int bookCount=jsonpath.getInt("book.size()");

        //validate at least one book should be in store
        assertThat(bookCount, greaterThan(0));

        //print all the titles of books in the store
        for (int i = 0; i < bookCount; i++) {
            String bookTitle=jsonpath.getString("book["+i+"].title");
            System.out.println(bookTitle);
        }

        // search for a particular book
        boolean status=false;
        for (int i = 0; i < bookCount; i++) {
            String bookTitle=jsonpath.getString("book["+i+"].title");
            if(bookTitle.equals("The Lord of the Rings")){
                status=true;
                break;
            }
        }
        assertThat(status, is(true));
        
        // calculate and validate the total price of all four books
        double totalPrice=0;
        for (int i = 0; i < bookCount; i++) {
            double bookPrice=jsonpath.getDouble("book["+i+"].price");
            totalPrice=totalPrice+bookPrice;
            }
        System.out.println("Total price of books: "+totalPrice);

        assertThat(totalPrice, is(53.92));
        }
    }