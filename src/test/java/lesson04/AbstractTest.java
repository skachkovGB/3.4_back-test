package lesson04;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public abstract class AbstractTest
{
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;
    private static String hash;
    private static String userName;


    private static ResponseSpecification responseSpecification;
    private static RequestSpecification requestSpecification;

    private static RequestSpecification requestSpecificationForPost;
    private static RequestSpecification requestSpecificationForAddMeal;



    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        baseUrl = prop.getProperty("baseUrl");
        hash = prop.getProperty("hash");
        userName = prop.getProperty("userName");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                //.expectHeader("Access-Control-Allow-Credentials","true")
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("includeNutrition", "false")
                //.addQueryParam("query", "burger")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        requestSpecificationForPost = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("includeNutrition", "false")
                .setContentType("application/x-www-form-urlencoded")
                .log(LogDetail.ALL)
                .build();

        requestSpecificationForAddMeal = new RequestSpecBuilder()
                .addQueryParam("apiKey", apiKey)
                .addQueryParam("includeNutrition", "false")
                .addQueryParam("hash", hash)
                .addPathParams("userName", userName)
                .setContentType("application/x-www-form-urlencoded")
                .log(LogDetail.ALL)
                .build();

        //RestAssured.responseSpecification = responseSpecification;
        //RestAssured.requestSpecification = requestSpecification;

    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getHash() { return hash; }

    public static String getUserName() { return userName; }

    public static ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }
    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public static RequestSpecification getRequestSpecificationForPost() {
        return requestSpecificationForPost;
    }
    public static RequestSpecification getRequestSpecificationForAddMeal() {
        return requestSpecificationForAddMeal;
    }
}
