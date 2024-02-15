package api;

import api.dto.BaseModel;
import api.dto.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import listener.TestListener;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

@Listeners(TestListener.class)
public class ApiBaseTest {

    protected ProductApiService apiService;
    protected ApiClient apiClient;
   protected Logger logger = Logger.getLogger(ApiBaseTest.class.getName());


    @BeforeTest
    public void createClient() {
        apiClient = new ApiClient(new RetrofitClient());
    }


    protected <T> Object jsonMapper(Class<T> type) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(new File("properties/product.json"), Product.class);
        return product;
    }

    public JsonElement getObjectFromJsonFile(String filePath) throws FileNotFoundException {
        return JsonParser.parseReader(new FileReader(filePath));

    }

}


