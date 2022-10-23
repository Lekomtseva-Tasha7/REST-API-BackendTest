package lesson4;

import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CuisineTest extends AbstractTest {
    //https://api.spoonacular.com/recipes/cuisine?title=Italian Pasta Salad with organic Arugula

    @Test
    void responseBodyHasValidSchemaTest () {

        CuisineResponse response = given().spec(getRequestSpecification2())
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .extract()
                .response()
                .body()
                .as(CuisineResponse.class);

        assertThat(response.getCuisine().getClass().toString(), equalTo("class java.lang.String"));
        assertThat(response.getCuisines().getClass().toString(), equalTo("class java.util.ArrayList"));
        assertThat(response.getConfidence().getClass().toString(), equalTo("class java.lang.Double"));
    }

    @Test
    void statusCodeIs200Test (){

        given().spec(getRequestSpecification2())
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void responseTimeIsLessThan700msTest(){

        given().spec(getRequestSpecification2())
                .when()
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek()
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void contentTypeApplicationJsonTest (){
        Response response = given().spec(getRequestSpecification2())
                .when()
                .post(getBaseUrl() + "recipes/cuisine").prettyPeek();

        assertThat(response.getHeader("Content-Type"), equalTo("application/json"));
    }
}
