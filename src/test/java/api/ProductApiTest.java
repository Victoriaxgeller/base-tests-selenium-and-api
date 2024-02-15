package api;

import api.dto.Product;
import asserts.ProductAssert;
import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProductApiTest extends ApiBaseTest {

    private static final int PRODUCT_ID_FOR_GET = 1;
    private ProductAssert anAssert = new ProductAssert();

    @Test(enabled = true, testName = "Get product by id with API call")
    public void checkProductById() throws IOException, IllegalAccessException {
        Product actual = apiClient.productActions().getProduct(PRODUCT_ID_FOR_GET);
        Product expectedProduct = new Gson().fromJson(getObjectFromJsonFile("properties/product.json"), Product.class);
        anAssert.compareProducts(actual, expectedProduct);
    }

    @Test(enabled = true, testName = "Add new product with API call")
    public void addNewProduct() throws IOException, IllegalAccessException {
        Product expectedProduct = new Gson().fromJson(getObjectFromJsonFile("properties/product_for_post.json"), Product.class);
        Product actual = apiClient.productActions().createProduct(expectedProduct);
        anAssert.compareProducts(actual, expectedProduct);

    }
}
