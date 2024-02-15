package asserts;

import api.ApiBaseTest;
import api.dto.Product;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Arrays;

@Log
public class ProductAssert extends ApiBaseTest {


    @Test(enabled = true)
    public void test() throws FileNotFoundException, IllegalAccessException {
        Product actual = new Gson().fromJson(getObjectFromJsonFile("properties/product_for_post.json"), Product.class);
        Product preparedProduct = new Gson().fromJson(getObjectFromJsonFile("properties/product.json"), Product.class);

        compareProducts(actual, preparedProduct);

    }

    public void compareProducts(Product actual, Product expected) throws IllegalAccessException {
        SoftAssert softAssert = new SoftAssert();
        if (expected.getId() == 0) {
            expected.setId(actual.getId());
        }
        for (Field field : actual.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value1 = field.get(actual);
            Object value2 = field.get(expected);
            if (value1 != null && value2 != null) {
                softAssert.assertEquals(value1, value2);
            }
        }

    }
}
