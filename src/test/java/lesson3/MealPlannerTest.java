package lesson3;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class MealPlannerTest extends AbstractTest {
    static String date = "2020-06-01";

    @Test
    void getMealPlanWeek() {
        //https://api.spoonacular.com/mealplanner/{username}/week/{start-date}
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .pathParam("start-date", date)
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/week/{start-date}")
                .then()
                .statusCode(200);
    }

    @Test
    void addToMealPlan (){
        //https://api.spoonacular.com/mealplanner/{username}/items
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .body("{\n"
                        + "\"date\": 1589500800,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"INGREDIENTS\",\n"
                        + "\"value\": {\n"
                        + "\"ingredients\": [\n"
                        + "{\n"
                        + "\"name\": \"1 banana\"\n"
                        + "}\n"
                        + "]\n"
                        + "}\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/items")
                .then()
                .statusCode(200);
    }

    @Test
    void generateMealPlan () {
        //https://api.spoonacular.com/mealplanner/generate&?timeFrame=day
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .queryParam("timeFrame", "day")
                .when()
                .get(getBaseUrl() + "mealplanner/generate")
                .then()
                .statusCode(200);
    }

    @Test
    void addDeleteToMealPlan () {
        //https://api.spoonacular.com/mealplanner/{username}/items
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        //https://api.spoonacular.com/mealplanner/{username}/items/{id}
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .delete(getBaseUrl() + "mealplanner/{username}/items/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void clearMealPlanDay () {
        //https://api.spoonacular.com/mealplanner/{username}/day/{date}
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .pathParam("date", date)
                .when()
                .delete(getBaseUrl() + "mealplanner/{username}/day/{date}")
                .then()
                .statusCode(200);
    }

    @Test
    void mealPlanTemplates () {
        //https://api.spoonacular.com/mealplanner/{username}/templates
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .body("{\n"
                        + "\"name\": \"My new meal plan template\",\n"
                        + "\"items\": [\n"
                        + "{\n"
                        + "\"day\": 1,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"RECIPE\",\n"
                        + "\"value\": {\n"
                        + "\"id\": 296213,\n"
                        + "\"servings\": 2,\n"
                        + "\"title\": \"Spinach Salad with Roasted Vegetables and Spiced Chickpea\",\n"
                        + "\"imageType\": \"jpg\"\n"
                        + "}\n"
                        + "},\n"
                        + "{\n"
                        + "\"day\": 2,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"PRODUCT\",\n"
                        + "\"value\": {\n"
                        + "\"id\": 183433,\n"
                        + "\"servings\": 1,\n"
                        + "\"title\": \"Ahold Lasagna with Meat Sauce\",\n"
                        + "\"imageType\": \"jpg\"\n"
                        + "}\n"
                        + "},\n"
                        + "{\n"
                        + "\"day\": 3,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"MENU_ITEM\",\n"
                        + "\"value\": {\n"
                        + "\"id\": 378557,\n"
                        + "\"servings\": 1,\n"
                        + "\"title\": \"Pizza 73 BBQ Steak Pizza, 9\",\n"
                        + "\"imageType\": \"png\"\n"
                        + "}\n"
                        + "},\n"
                        + "{\n"
                        + "\"day\": 4,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"CUSTOM_FOOD\",\n"
                        + "\"value\": {\n"
                        + "\"id\": 348,\n"
                        + "\"servings\": 1,\n"
                        + "\"title\": \"Aldi Spicy Cashews - 30g\",\n"
                        + "\"image\": \"https://spoonacular.com/cdn/ingredients_100x100/cashews.jpg\"\n"
                        + "}\n"
                        + "},\n"
                        + "{\n"
                        + "\"day\": 5,\n"
                        + "\"slot\": 1,\n"
                        + "\"position\": 0,\n"
                        + "\"type\": \"INGREDIENTS\",\n"
                        + "\"value\": {\n"
                        + "\"ingredients\": [\n"
                        + "{\n"
                        + "\"name\": \"1 banana\"\n"
                        + "},\n"
                        + "{\n"
                        + "\"name\": \"coffee\",\n"
                        + "\"unit\": \"cup\",\n"
                        + "\"amount\": \"1\",\n"
                        + "\"image\": \"https://spoonacular.com/cdn/ingredients_100x100/brewed-coffee.jpg\"\n"
                        + "}\n"
                        + "]\n"
                        + "}\n"
                        + "}\n"
                        + "],\n"
                        + "\"publishAsPublic\": false\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/templates")
                .then()
                .statusCode(200);

        //https://api.spoonacular.com/mealplanner/{username}/templates
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/templates")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("templates[0].id")
                .toString();

        //https://api.spoonacular.com/mealplanner/{username}/templates/{id}
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .when()
                .get(getBaseUrl() + "mealplanner/{username}/templates/" + id)
                .then()
                .statusCode(200);

        //https://api.sponacular.com/mealplanner/{username}/templates/{id}
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParam("username", getUsername())
                .delete(getBaseUrl() + "mealplanner/{username}/templates/" + id)
                .then()
                .statusCode(200);
    }

}
