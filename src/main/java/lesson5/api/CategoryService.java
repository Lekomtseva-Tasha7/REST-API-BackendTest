package lesson5.api;

import lesson5.dto.GetCategoryResponse;
import lesson5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface CategoryService {
    @GET("categories/{id}")
    Call<GetCategoryResponse> getCategory(@Path("id") int id);
    //Call<ResponseBody> getCategory(@Path("id") int id); // если тип результата — null или результат неважен, то есть мы не собираемся его парсить в дальнейшем.

   /* @GET("categories")
    Call<ResponseBody> getCategories();

    @POST("categories")
    Call<GetCategoryResponse> postCategories(@Body Product createProductRequest);

    @PUT("categories")
    Call<GetCategoryResponse> putCategories(@Body Product modifyProductRequest);

    @DELETE("categories/{id}")
    Call<ResponseBody> deleteCategory(@Path("id") int id);*/

}
