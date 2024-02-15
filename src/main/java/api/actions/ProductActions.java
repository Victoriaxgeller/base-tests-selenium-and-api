package api.actions;

import api.ProductApiService;
import api.dto.Product;

import java.io.IOException;

public class ProductActions {

    private ProductApiService call;

    public ProductActions(ProductApiService call) {
        this.call = call;
    }

    public Product getProduct(int id) throws IOException {
        return call.getProduct(id).execute().body();
    }

    public Product createProduct(Product product) throws IOException {
        return call.addProduct(product).execute().body();
    }
}
