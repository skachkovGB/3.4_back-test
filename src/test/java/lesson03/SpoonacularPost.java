package lesson03;

import io.restassured.path.json.JsonPath;
        import org.junit.jupiter.api.Test;
        import java.util.List;
        import java.util.concurrent.TimeUnit;

        import static io.restassured.RestAssured.given;
        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.*;

public class SpoonacularPost extends AbstractTest
{
    @Test
    void postClassifyCuisineTitle()
    {
        JsonPath response = given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "burger")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertThat(response.get("cuisine"), is("American"));
    }

    @Test
    void postClassifyCuisineTitleOneMore()
    {
        JsonPath response = given()
                .contentType("application/x-www-form-urlencoded")
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "tacos")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertThat(response.get("cuisine"), is("Mexican"));
    }

    @Test
    void postClassifyCuisineTimeResp()
    {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "burger")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .time(lessThan(900L), TimeUnit.MILLISECONDS);
    }

    @Test
    void postClassifyCuisinesContains()
    {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "123")
                .expect()
                .body("cuisines", contains("Mediterranean","European","Italian"))
                .when()
                .post(getBaseUrl() +"recipes/cuisine");
    }

    @Test
    void postClassifyCuisinesConfidenceCheck()
    {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "123")
                .expect()
                .body("confidence", equalTo(0.0F))
                .when()
                .post(getBaseUrl() + "recipes/cuisine");

    }

    @Test
    void postClassifyCuisinesNotNull()
    {
        String response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "123")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .jsonPath().toString();
        assertThat(response, notNullValue());
    }
}
