package lesson04;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpoonPost extends AbstractTest
{

    @Test
    void postClassifyCuisineTitle()
    {
        PostResponseJson response = given()
                .spec(getRequestSpecificationForPost())
                .formParam("title", "burger")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .as(PostResponseJson.class);
        assertThat(response.getCuisine(), is("American"));

    }

    @Test
    void postClassifyCuisineTitleOneMore()
    {
        PostResponseJson response = given()
                .spec(getRequestSpecificationForPost())
                .formParam("title", "tacos")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .as(PostResponseJson.class);
        assertThat(response.getCuisine(), is("Mexican"));
    }

    @Test
    void postClassifyCuisineTimeResp()
    {
        given()
                .spec(getRequestSpecificationForPost())
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(getResponseSpecification())
                .time(lessThan(900L), TimeUnit.MILLISECONDS);
    }


    @Test
    void postClassifyCuisinesContains()
    {
        given()
                .spec(getRequestSpecificationForPost())
                .expect()
                .body("cuisines", contains("Mediterranean","European","Italian"))
                .when()
                .post(getBaseUrl() +"recipes/cuisine")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void postClassifyCuisinesConfidenceCheck()
    {
        given()
                .spec(getRequestSpecificationForPost())
                .expect()
                .body("confidence", equalTo(0.0F))
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .then()
                .spec(getResponseSpecification());

    }

    @Test
    void addAndDelMealTest()
    {
        String id = given()
                .spec(getRequestSpecificationForAddMeal())
                .body("{\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + "}")
                .when()
                .post(getBaseUrl() + "mealplanner/{userName}/shopping-list/items")
                .jsonPath()
                .get("id")
                .toString();

        given()
                .spec(getRequestSpecificationForAddMeal())
                .pathParams("id", id)
                .when()
                .delete(getBaseUrl() + "mealplanner/{userName}/shopping-list/items/{id}")
                .then()
                .spec(getResponseSpecification());
}

    @Test
    void getShoppingList()
    {
        given()
                .spec(getRequestSpecification())
                .queryParam("hash", getHash())
                .pathParams("user_name", getUserName())
                .expect()
                .body("cost", equalTo(0.0F))
                .when()
                .get(getBaseUrl() + "mealplanner/{user_name}/shopping-list")
                .then()
                .spec(getResponseSpecification());
    }
}

