package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


// obidve metody ParsingComplexJSONResponseTest a testUserDetailsValidation su v ramci jednej public class
public class ParsingComplexJSONResponseTest { // metoda cislo 1

    // user defined method - this will read data from json, convert to json response and return it

    JSONObject getJSONResponse() { // metoda, ktora recept prinesie pripraveny na pouzitie
        File myfile=new File("src/main/resources/complex.json"); // file - kniha s receptami, definicia kde sa kniha nachadza
        FileReader fileReader=null; // stroj na citanie knihy stranu po strane
        // try-catch blok - program nespadne, ked subor myfile nenajde
        try {
            fileReader=new FileReader(myfile);
        } catch (FileNotFoundException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }

        // stroj, ktory prelozi vystup z fileReader do citatelnej podoby
        JSONTokener jsonTokener=new JSONTokener(fileReader);

        // dava recept z knihy myfile precitany fileReaderom a prelozeny JSONTokenerom do jasnej struktury ingrediencia - mnozstvo
        JSONObject jsonResponse=new JSONObject(jsonTokener);
        return jsonResponse; // recept pripraveny na pouzitie
    }

    @Test(priority = 1) // priorita je metoda z testNG
    void testUserDetailsValidation(){ // metoda cislo 2
        // volame metodu JSONResponse ktora nam poskytne JSONObject - pripraveny recept, prekonvertuje sa nam na String - suvisly text
        String str=getJSONResponse().toString();
        JsonPath jsonPath=new JsonPath(str);

        // a) skontrolovat status z JSON Path
        String status=jsonPath.getString("status");
        assertThat(status, is("success")); // kontrolujem ci vystup obsahuje slovo "success"

        // b) skontrolovat/zvalidovat user details
        int id=jsonPath.getInt("data.userDetails.id"); // vrati hodnotu "id". cely statement ulozeny do integeru/cisla id
        String name=jsonPath.getString("data.userDetails.name"); // vrati hodnotu name, ale slovo String, nie cislo integer
        String email=jsonPath.getString("data.userDetails.email"); // podobne ako vyssie

        assertThat(id,is(12345));
        assertThat(name,is("John Doe"));
        assertThat(email,is("john.doe@example.com"));

        // c) skontrolovat home phone number
        String homePhoneType=jsonPath.getString("data.userDetails.phoneNumbers[0].type"); // specifikuje "home" phone number
        String homePhone=jsonPath.getString("data.userDetails.phoneNumbers[0].number"); // specifikuje telefonne cislo

        assertThat(homePhoneType, is("home"));
        assertThat(homePhone, is("123-456-7890"));

        // d) verifikovat geo koordinaty
        double latitude=jsonPath.getDouble("data.userDetails.address.geo.latitude");
        double longitude=jsonPath.getDouble("data.userDetails.address.geo.longitude");

        assertThat(latitude,is(39.7817));
        assertThat(longitude,is(-89.6501));

        // e) zvalidovat zapnute/vypnute notifikacie a theme v preferences
        boolean notifications=jsonPath.getBoolean("data.userDetails.preferences.notifications");
        String theme=jsonPath.getString("data.userDetails.preferences.theme");

        assertThat(notifications,is(true));
        assertThat(theme,is("dark"));
    }
}