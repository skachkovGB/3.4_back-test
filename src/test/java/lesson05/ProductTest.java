package lesson05;

import lesson05.api.ProductService;
import lesson05.dto.Product;
import lesson05.utils.RetrofitUtils;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ProductTest {

    private static ProductService productService;
    private static Product product;
    private static Product productModify;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);

        product = new Product()
                .withTitle("Milk")
                .withCategoryTitle("Food")
                .withPrice(100);
    }


    @Test
    @Order(1)
    void createProductTest() throws IOException {
        Response<Product> response = productService.createProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @Order(2)
    void getProductByIdTest() throws IOException {
        Response<Product> response = productService.getProductById(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
    }

    @Test
    @Order(3)
    void getProductByIdNegativeTest() throws IOException {
        Response<Product> response = productService.getProductById(20).execute();
        assertThat(response.code(), equalTo(404));
    }

    @Test
    @Order(4)
    void getAllProductsTest() throws IOException {
        Response<ResponseBody> response = productService.getProducts().execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    @Order(5)
    void getProductCheckBeforeModify() throws IOException {
        Response<Product> response = productService.getProductById(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assertThat(response.body().getTitle(), equalTo("Milk"));
    }

    @Test
    @Order(6)
    void modifyProductTest() throws IOException{
        Response<Product> response = productService.modifyProduct(
                new Product()
                        .withId(1)
                        .withTitle("Bread")
                        .withCategoryTitle("Food")
                        .withPrice(50))
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getTitle(), equalTo("Bread"));
    }

    @Test
    @Order(7)
    void deleteProductTest() throws IOException{
        Response<ResponseBody> response = productService.deleteProduct(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

}
