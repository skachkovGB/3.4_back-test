package lesson03;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
        import static io.restassured.RestAssured.given;
        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.*;


public class SpoonacularGet extends AbstractTest
{

    @Test
    void getComplexSearch200BurgerCorrect() {
        given()
            .queryParam("apiKey", getApiKey())
            .queryParam("query", "burger")
            .log().all()
            .when()
            .get(getBaseUrl() + "recipes/complexSearch")
            .prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    void getComplexSearchBurgerWrong() {
        JsonPath response = given()
            .queryParam("apiKey", getApiKey())
            .queryParam("query", "bburger")
            .when()
            .get(getBaseUrl() + "recipes/complexSearch")
            .body()
            .jsonPath();
        assertThat(response.get("totalResults"), is(0));


    }

    @Test
    void getComplexSearchWrongApiKey() {
        given()
                .queryParam("apiKey", "fd307cfad7aa4422a6171d0dea47d464")
                .queryParam("query", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(401);
    }

    @Test
    void getComplexSearchBigWrongTime() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "burger")
                .queryParam("maxReadyTime", "100000000000000000000000000000000")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(404);
    }

    @Test
    void getComplexSearchConflictCarbs() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("minCarbs", "60")
                .queryParam("maxCarbs", "50")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertThat(response.get("totalResults"), is(0));
    }

    @Test
    void addMealTest()
    {
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParams("userName", getUserName())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .log().all()
                .when()
                .post(getBaseUrl() + "mealplanner/{userName}/shopping-list/items")
                .prettyPeek()
                .jsonPath()
                .get("id")
                .toString();


        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .pathParams("userName", getUserName())
                .pathParams("id", id)
                .log().all()
                .when()
                .delete(getBaseUrl() + "mealplanner/{userName}/shopping-list/items/{id}")
                .prettyPeek();
    }
}
