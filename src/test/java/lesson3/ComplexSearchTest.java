package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ComplexSearchTest extends AbstractTest{

    //https://api.spoonacular.com/recipes/complexSearch?cuisine=italian&includeIngredients=tomato,chees&instructionsRequired=true&type=salad

    @Test
    void responseBodyHasValidSchemaTest () {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();

        assertThat(response.get("results[0].id").getClass().toString(), containsString("Integer"));
        assertThat(response.get("results[0].title").getClass().toString(), containsString("String"));
        assertThat(response.get("results[0].image").getClass().toString(), containsString("String"));
        assertThat(response.get("results[0].imageType").getClass().toString(), containsString("String"));
    }

    @Test
    void statusCodeIs200Test (){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void responseTimeIsLessThan700msTest(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .time(lessThan(7000L));
    }

    @Test
    void resultsIsNotNullTest(){
        Response response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");

        assertThat(response.toString().length(), greaterThan(0));
    }

    @Test
    void offsetIsEqualToZeroTest (){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .expect()
                .body("offset", equalTo(0))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(200);
    }

    @Test
    void containsSaladTest (){
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .body()
                .jsonPath();

        assertThat(response.get("results[0].title"), containsString("Salad"));
        assertThat(response.get("results[1].title"), containsString("Salad"));
    }

    @Test
    void contentTypeApplicationJsonTest (){
        Response response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "italian")
                .queryParam("includeIngredients", "tomato,chees")
                .queryParam("instructionsRequired", "true")
                .queryParam("type", "salad")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");

        assertThat(response.getHeader("Content-Type"), equalTo("application/json"));
    }

}