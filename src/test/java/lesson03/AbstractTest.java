package lesson03;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest
{
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;
    private static String hash;
    private static String userName;



    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        apiKey = prop.getProperty("apiKey");
        baseUrl = prop.getProperty("baseUrl");
        hash = prop.getProperty("hash");
        userName = prop.getProperty("userName");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getHash() {
        return hash;
    }

    public static String getUserName() {
        return userName;
    }
}