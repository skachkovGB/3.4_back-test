package lesson04;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SpoonGet extends AbstractTest {


    @Test
    void getComplexSearch200BurgerCorrect() {
        given()
                .spec(getRequestSpecification())
                .queryParam("query", "burger")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification());
    }

    @Test
    void getComplexSearchBurgerWrong() {
        GetResposeJson response = given()
                .queryParam("query", "bburger")
                .spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .as(GetResposeJson.class);

        assertThat(response.getTotalResults(), is(0));
    }

    @Test
    void getComplexSearchConflictCarbs() {
        GetResposeJson response = given()
                .spec(getRequestSpecification())
                .queryParam("minCarbs", "60")
                .queryParam("maxCarbs", "50")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .as(GetResposeJson.class);
        assertThat(response.getTotalResults(), is(0));
    }


    @Test
    void getComplexSearchCorrectCarbs() {
        GetResposeJson response = given()
                .spec(getRequestSpecification())
                .queryParam("minCarbs", "50")
                .queryParam("maxCarbs", "55")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .body()
                .as(GetResposeJson.class);
        assertThat(response.getTotalResults(), is(180));
    }
}


