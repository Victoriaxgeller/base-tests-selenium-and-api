package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;


public class RetrofitClient {

    private ProductApiService service;

    public static final String AUTH_HEADER_NAME = "Authorization";
    private static final String PRODUCT_URL = "https://dummyjson.com/";

    public RetrofitClient(String JWT) {
        createRequest(JWT);
    }

    public RetrofitClient() {
        createRequest("");
    }

    private void createRequest(String JWT) {
        Gson gson = new GsonBuilder()
                .setLenient().create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HTTPLogInterceptor())
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader(AUTH_HEADER_NAME, "JWT " + JWT)
                            .addHeader("X-Forwarded-For", "193.0.220.199")
                            .build();
                    return chain.proceed(request);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(PRODUCT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ProductApiService.class);
    }

    public ProductApiService service() {
        return service;
    }
}
