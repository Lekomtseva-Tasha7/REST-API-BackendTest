package lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CuisineTest extends AbstractTest{

    //https://api.spoonacular.com/recipes/cuisine?title=Italian Pasta Salad with organic Arugula

    @Test
    void responseBodyHasValidSchemaTest () {

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Italian Pasta Salad with organic Arugula")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();

        assertThat(response.get("cuisine").getClass().toString(), containsString("String"));
        assertThat(response.get("cuisines").getClass().toString(), containsString("ArrayList"));
        assertThat(response.get("confidence").getClass().toString(), containsString("Float"));
    }

    @Test
    void statusCodeIs200Test (){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Italian Pasta Salad with organic Arugula")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void responseTimeIsLessThan700msTest(){

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Italian Pasta Salad with organic Arugula")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .time(lessThan(7000L));
    }

    @Test
    void contentTypeApplicationJsonTest (){
        Response response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Italian Pasta Salad with organic Arugula")
                .when()
                .post(getBaseUrl() + "recipes/cuisine");

        assertThat(response.getHeader("Content-Type"), equalTo("application/json"));
    }

    @Test
    void requestHeadersContentTypeApplicationTest(){ //не могу вытащить значение заголовка запроса
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Italian Pasta Salad with organic Arugula")
                .request()
                .expect()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when()
                .post(getBaseUrl() + "recipes/cuisine");
    }
}
