package api;

import api.actions.ProductActions;

public class ApiClient {
    private ProductApiService productApiService;

    public ApiClient(final RetrofitClient retrofitClient) {
        this.productApiService = retrofitClient.service();

    }


    public ProductActions productActions() {
        return new ProductActions(productApiService);
    }
}
