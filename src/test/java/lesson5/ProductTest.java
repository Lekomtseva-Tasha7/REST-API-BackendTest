package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import java.io.IOException;

import static lesson5.utils.RetrofitUtils.getMyId;
import static lesson5.utils.RetrofitUtils.setMyId;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @SneakyThrows
    @Test
    @Order(1)
    //GET "http://localhost:8189/market/api/v1/products"
    void returnsProductsTest (){
        Response<ResponseBody> response = productService.getProducts()
                .execute();
        assertThat(response.code(), CoreMatchers.is(200));
        assertThat(response.headers().get("content-type"), CoreMatchers.is("application/json"));
        assertThat(response.headers().get("connection"), CoreMatchers.is("keep-alive"));
    }

    @Test
    @Order(2)
    //POST "http://localhost:8189/market/api/v1/products"
    void createProductInFoodCategoryTest() throws IOException {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
        Response<Product> response = productService.createProduct(product)
                .execute();
        assert response.body() != null;
        setMyId(response.body().getId());
        assertThat(response.code(), CoreMatchers.is(201));
    }

    @SneakyThrows
    @Test
    @Order(3)
    //PUT "http://localhost:8189/market/api/v1/products"
    void modifyProductPositiveTest () throws IOException {
        product = new Product()
                .withId(getMyId())
                .withTitle("Bread")
                .withCategoryTitle("Food")
                .withPrice(82);
        Response<Product> response = productService.modifyProduct(product)
                .execute();
        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getPrice(), CoreMatchers.is(82));
    }

    @SneakyThrows
    @Test
    @Order(4) // вместо ошибки 404 выдает 400
    void modifyProductCode404Test () throws IOException {
        product = new Product()
                .withId(1001)
                .withTitle("Bread")
                .withCategoryTitle("Food")
                .withPrice(82);
        Response<Product> response = productService.modifyProduct(product)
                .execute();
        assertThat(response.code(), CoreMatchers.is(404));
    }

    @Test
    @Order(5)
    //GET "http://localhost:8189/market/api/v1/products/id"
    void returnsSpecificProductByIdTest () throws IOException {
       Response<Product> response = productService.getProductById(getMyId())
                .execute();
        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @Order(6)
    void returnsSpecificProductByIdCode404Test () throws IOException {
        Response<Product> response = productService.getProductById(1001)
                .execute();
        assertThat(response.code(), CoreMatchers.is(404));
    }

    @SneakyThrows
    @Test
    @Order(7)
    //DELETE "http://localhost:8189/market/api/v1/products/id"
    void deleteProductTest() {
        Response<ResponseBody> response = productService.deleteProduct(getMyId()).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @SneakyThrows
    @Test
    @Order(8) //Вместо 404 (страница не найдена) показывает 500 (ошибка сервера)
    void deleteProductCode404Test() {
        Response<ResponseBody> response = productService.deleteProduct(1001).execute();
        assertThat(response.code(), CoreMatchers.is(404));
    }
}
