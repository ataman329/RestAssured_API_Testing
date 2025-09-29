package Day4;

import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FileUploadAndDownloadTest {

    //1. single file upload

    @Test

    void uploadSingleFileTest () {
        File myfile=new File(("C:\\Users\\Adrian\\Desktop\\API skolenie UDEMY\\automationFiles\\Test1.txt"));
        given()
                .multiPart("file", myfile)
                .contentType("multipart/form-data")
        .when()
                .post("http://localhost:8080/uploadFile")
        .then()
                .statusCode(200)
                .body("fileName", equalTo("Test1.txt"))
                .log().body();
    }

    //2. multiple files upload

    @Test
    void uploadMultipleFilesTest () {
        File myfile1=new File(("C:\\Users\\Adrian\\Desktop\\API skolenie UDEMY\\automationFiles\\Test1.txt"));
        File myfile2=new File(("C:\\Users\\Adrian\\Desktop\\API skolenie UDEMY\\automationFiles\\Test2.txt"));
        given()
                .multiPart("files", myfile1)
                .multiPart("files", myfile2)
                .contentType("multipart/form-data")
                .when()
                .post("http://localhost:8080/uploadMultipleFiles")
                .then()
                .statusCode(200)
                .body("[0].fileName", equalTo("Test1.txt"))
                .body("[1].fileName", equalTo("Test2.txt"))
                .log().body();
    }

    //3. single file download
    @Test
    void downloadFile() {
        given()
                .pathParam("filename", "Test1.txt")
        .when()
                .get("http://localhost:8080/downloadFile/{filename}")
        .then()
                .statusCode(200)
                .log().body();
    }
}