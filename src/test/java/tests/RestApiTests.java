package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("QA.GURU QA automation course")
@Feature("Work with REST API")
@Story("REST API tests with REST Assured")
@Tag("rest_api_tests")
class RestApiTests extends TestBase {
    public String startUrl = "https://reqres.in";
    @Test
    void simpleGetRestAssured() {
        String usersList = get(startUrl + "/api/users?page=2").asString();
        System.out.println(usersList);
    }
    @Test
    void simpleApiGetJUnitAssertTrue() {
        String usersList = get(startUrl + "/api/users?page=2").asString();
        assertTrue(usersList.length() > 0, "usersList length: " +
                usersList.length() + " with data: " + usersList);
    }
    @Test
    void simpleApiGetHamcrestAssertThat() {
        String usersList = get(startUrl + "/api/users?page=2").asString();
        assertThat(usersList.length(), is(not(nullValue())));
    }
    @Test
    void restAssuredNormalget() {
        RestAssured.baseURI = "https://reqres.in";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/users?page=2");
        System.out.println(response.getBody().asString());
    }
    @Test
    void parseJsonFromApiGetSimplified() {
        RestAssured.baseURI = "https://reqres.in";
        get("/api/users?page=2")
                .then()
                .body("total", is(12));
    }
    @Test
    void parseJsonFromApiGet() {
        RestAssured.baseURI = "https://reqres.in";
        step("hello", ()-> {
            Integer resultsTotal = given()
                    .when()
                    .get("/api/users?page=2")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response()
                    .path("total");
            assertThat(resultsTotal, is(12));

        });
    }


}

