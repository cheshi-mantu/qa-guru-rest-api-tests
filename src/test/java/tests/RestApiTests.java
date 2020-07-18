package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
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
    private String baseUrl = "https://reqres.in";
    Integer resultsTotal;
    String usersList;

    @Test
    @Description("Simple RestAssured get request and send to System.out")
    void simpleGetRestAssured() {
        step("get by RestAssured, print to the std system output", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            System.out.println(usersList);
        });
    }

    @Test
    @Description("Simple RestAssured with assert by JUnit5 means usersList length > 0")
    void simpleApiGetJUnitAssertTrue() {
        step("get with rest assured, assert by JUnit5 with comments", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            assertTrue(usersList.length() > 0, "usersList length: " +
                    usersList.length() + " with data: " + usersList);
        });

    }
    @Test
    @Description("Simple RestAssured get request assertion by means of hamcrest assertThat ans \'is\'")
    void simpleApiGetHamcrestAssertThat() {
        step("set usersList as the result of the get response by RestAssured" +
                "check usersList length is not null (hamcrest)", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            assertThat(usersList.length(), is(not(nullValue())));
        });
    }

    @Test
    void restAssuredNormalget() {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/users?page=2");
        System.out.println(response.getBody().asString());
    }
    @Test
    void parseJsonFromApiGetSimplified() {
        RestAssured.baseURI = baseUrl;
        get("/api/users?page=2")
                .then()
                .body("total", is(12));
    }
    @Test
    void parseJsonFromApiGetHamcrestAssert() {
        step("Initialize baseURI = " + baseUrl, ()->{
            RestAssured.baseURI = baseUrl;
        });
        step("Assign the results of REST API resuest to resultsTotal from JSON var total", ()-> {
            resultsTotal = given()
                    .filter(new AllureRestAssured())
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
    @Test
    void parseJsonFromApiGetRestAssrdOnly() {
        RestAssured.baseURI = baseUrl;
        step("hello", ()-> {
                given()
                .filter(new AllureRestAssured())
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
        });
    }
    @Test
    void parseJsonParseJsonDeeper() {
        RestAssured.baseURI = baseUrl;
        step("hello", ()-> {
                given()
                .filter(new AllureRestAssured())
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
        });
    }


}

