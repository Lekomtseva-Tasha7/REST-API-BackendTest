package lesson4;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ComplexSearchTest extends AbstractTest {

    //https://api.spoonacular.com/recipes/complexSearch?cuisine=italian&includeIngredients=tomato,chees&instructionsRequired=true&type=salad
    @Test
    void responseBodyHasValidSchemaTest () {

        ComplexSearchResponse response = given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ComplexSearchResponse.class);

        assertThat(response.getResults().get(0).getId().getClass().toString(), containsString("Integer"));
        assertThat(response.getResults().get(0).getTitle().getClass().toString(), containsString("String"));
        assertThat(response.getResults().get(0).getImage().getClass().toString(), containsString("String"));
        assertThat(response.getResults().get(0).getImageType().getClass().toString(), containsString("String"));
    }

    @Test
    void statusCodeIs200Test (){

        given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .assertThat()
                .spec(getResponseSpecification());
    }

    @Test
    void responseTimeIsLessThan700msTest(){

        given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void resultsIsNotNullTest(){

        Response response = given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");

        assertThat(response.toString().length(), greaterThan(0));
    }

    @Test
    void offsetIsEqualToZeroTest (){

        given().spec(getRequestSpecification())
                .expect()
                .body("offset", equalTo(0))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void containsSaladTest (){
        ComplexSearchResponse response = given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .extract()
                .response()
                .body()
                .as(ComplexSearchResponse.class);

        for (int i = 0; i < response.getResults().size(); i++) {
            assertThat(response.getResults().get(i).getTitle(), containsString("Salad"));
        }
    }

    @Test
    void contentTypeApplicationJsonTest (){
        Response response = given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");

        assertThat(response.getHeader("Content-Type"), equalTo("application/json"));
    }

}