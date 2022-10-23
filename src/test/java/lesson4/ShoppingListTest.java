package lesson4;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class ShoppingListTest extends AbstractTest{

    @Test
    void shoppingListTest (){
        // https://api.spoonacular.com/mealplanner/{username}/shopping-list/items
        String id = given().spec(getRequestSpecification3())
                .body("{\n"
                        + "\"aisles\": [\n"
                        + "{\n"
                        + "\"aisle\": \"Baking\",\n"
                        + "\"items\": [\n"
                        + "{\n"
                        + "\"id\": 115388,\n"
                        + "\"name\": \"baking powder\",\n"
                        + "\"measures\": {\n"
                        + "\"original\": {\n"
                        + "\"amount\": 1.0,\n"
                        + "\"unit\": \"package\"\n"
                        + "},\n"
                        + "\"metric\": {\n"
                        + "\"amount\": 1.0,\n"
                        + "\"unit\": \"pkg\"\n"
                        + "},\n"
                        + "\"us\": {\n"
                        + "\"amount\": 1.0,\n"
                        + "\"unit\": \"pkg\"\n"
                        + "}\n"
                        + "},\n"
                        + "\"pantryItem\": false,\n"
                        + "\"aisle\": \"Baking\",\n"
                        + "\"cost\": 0.71,\n"
                        + "\"ingredientId\": 18369\n"
                        + "}\n"
                        + "]\n"
                        + "}\n"
                        + "],\n"
                        + "\"cost\": 0.71,\n"
                        + "\"startDate\": 1588291200,\n"
                        + "\"endDate\": 1588896000\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{username}/shopping-list/items")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        // https://api.spoonacular.com/mealplanner/{username}/shopping-list
        given().spec(getRequestSpecification3())
                .get(getBaseUrl() + "mealplanner/{username}/shopping-list")
                .then()
                .spec(getResponseSpecification());

        // https://api.spoonacular.com/mealplanner/{username}/shopping-list/items/{id}
        given().spec(getRequestSpecification3())
                .delete(getBaseUrl() + "mealplanner/{username}/shopping-list/items/" + id)
                .then()
                .spec(getResponseSpecification());
    }
}
