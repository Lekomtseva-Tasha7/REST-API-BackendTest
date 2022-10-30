package lesson5;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import lesson5.api.CategoryService;
import lesson5.dto.GetCategoryResponse;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CategoryTest {

    static CategoryService categoryService;
    Product product = null;
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    /*@SneakyThrows
    @Test
    void getCategoriesTest(){
        Response<ResponseBody> response = categoryService.getCategories().execute();
        assert response.body() != null;
        assertThat(response.code(), CoreMatchers.is(200));
    }

    @SneakyThrows
    @Test
    void postCategoriesTest(){
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));

        Response<GetCategoryResponse> response = categoryService.postCategories(product).execute();

        assertThat(response.code(), CoreMatchers.is(200));
    }*/

    @SneakyThrows
    @Test
    void getCategoryByIdPositiveTest() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

        assert response.body() != null;
        assertThat(response.isSuccessful(), CoreMatchers.is(true)); // код ответа 2**
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));
    }
}
