package lesson4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {

    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;
    private static String hash;
    private static String username;
    protected static RequestSpecification requestSpecification;
    protected static RequestSpecification requestSpecification2;
    protected static RequestSpecification requestSpecification3;
    protected static ResponseSpecification responseSpecification;

    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        baseUrl = prop.getProperty("base_url");
        hash = prop.getProperty("hash");
        username = prop.getProperty("username");

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("cuisine", "italian")
                .addQueryParam("includeIngredients", "tomato,chees")
                .addQueryParam("instructionsRequired", "true")
                .addQueryParam("type", "salad")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        requestSpecification2 = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("title", "Italian Pasta Salad with organic Arugula")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        requestSpecification3 = new RequestSpecBuilder()
                .addQueryParam("hash", hash)
                .addQueryParam("apiKey", apiKey)
                .addPathParam("username", username)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(7000L))
                .build();
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getHash() {
        return hash;
    }

    public static String getUsername() {
        return username;
    }

    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public static RequestSpecification getRequestSpecification2() {
        return requestSpecification2;
    }

    public static RequestSpecification getRequestSpecification3() {
        return requestSpecification3;
    }

    public static ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

}
