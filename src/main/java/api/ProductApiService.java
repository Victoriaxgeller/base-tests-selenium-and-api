package api;

import api.dto.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ProductApiService {


    String PRODUCT = "/products/{productId}";
    String ADD_PRODUCT = "/products/add/";


    @GET(PRODUCT)
    Call<Product> getProduct(@Path("productId") int productId);

    @POST("https://fakestoreapi.com/products")
    Call<Product> addProduct(@Body Product product);
}

