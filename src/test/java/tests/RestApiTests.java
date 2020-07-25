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
    String email;
    String returnedValue;

    @Test
    @Description("1. Simple RestAssured get request and sending it to System.out. No assertion.")
    void simpleGetRestAssured() {
        step("get by RestAssured, print to the std system output", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            System.out.println(usersList);
        });
    }

    @Test
    @Description("2. Simple RestAssured with assert by JUnit5 means usersList length > 0")
    void simpleApiGetJUnitAssertTrue() {
        step("get with rest assured, assert by JUnit5 with comments", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            assertTrue(usersList.length() > 0, "usersList length: " +
                    usersList.length() + " with data: " + usersList);
        });
    }

    @Test
    @Description("3. Simple RestAssured get request assertion by means of hamcrest assertThat ans \'is\'")
    void simpleApiGetHamcrestAssertThat() {
        step("set usersList as the result of the get response by RestAssured" +
                "check usersList length is not null (hamcrest)", ()-> {
            usersList = get(baseUrl + "/api/users?page=2").asString();
            assertThat(usersList.length(), is(not(nullValue())));
        });
    }

    @Test
    @Description("3.1 Simple RestAssured get request no assertion just different way to build the request")
    void restAssuredNormalget() {
        RestAssured.baseURI = baseUrl;
        RequestSpecification httpRequest = given();
        Response response = httpRequest.request(Method.GET, "/api/users?page=2");
        System.out.println(response.getBody().asString());
    }

    @Test
    @Description("4. Assert that returned result if 12 in one go, JSON parse by rest assured")
    void parseJsonFromApiGetSimplified() {
        RestAssured.baseURI = baseUrl;
            given()
            .log().all()
            .filter(new AllureRestAssured());
        get("/api/users?page=2")
            .then()
            .body("total", is(12));
    }

    @Test
    @Description("5. Std way to write rest-assured tests.")
    void parseJsonFromApiGetHamcrestAssert() {
        step("Initialize baseURI = " + baseUrl, ()->{
            RestAssured.baseURI = baseUrl;
        });
        step("Assign the results of REST API resuest to resultsTotal from JSON var total", ()-> {
            resultsTotal = given()
                    .filter(new AllureRestAssured())
                    .log().all()
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
    @Description("6. Variation of 5. Assertion by hamcrest's \'is\'")
    void parseJsonFromApiGetRestAssrdOnly() {
        RestAssured.baseURI = baseUrl;
        step("Build get request for group of users, getting the reponse. Assert.", ()-> {
                given()
                .filter(new AllureRestAssured())
                .log().all()
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
        });
    }
    @Test
    @Description("7. Deeper parsing of response. Parse response by index in array")
    void parseJsonParseJsonDeeper() {
        RestAssured.baseURI = baseUrl;
        step("hello", ()-> {
                given()
                    .log().all()
                    .filter(new AllureRestAssured());
            email = get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .path("data[2].email");
        });
        System.out.println("Email is;" + email);
        step("email should be tobias.funke@reqres.in", ()-> {
            assertThat(email, is("tobias.funke@reqres.in"));
        });
    }
    @Test
    @Description("8. Deeper parsing of response. Get response by key in dict")
    void parseJsonParseDictValues() {
        RestAssured.baseURI = baseUrl;
        step("creating get request for ", ()-> {
            given()
                    .filter(new AllureRestAssured())
                    .log().all();
            returnedValue = get("/api/users?page=2")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .path("ad.company");
        });
        System.out.println("Response value from BE:" + returnedValue);
        step("Should be StatusCode Wekly", ()-> {
            assertThat(returnedValue, is("StatusCode Weekly"));
        });
    }
    @Test
    @Description("9. Single User data check")
    void parseJsonForSingleUser() {
        RestAssured.baseURI = baseUrl;
        step("Creating get request for single user \'/api/users/2\' ", ()-> {
            given()
                    .filter(new AllureRestAssured())
                    .log().all();
            returnedValue = get("/api/users/2")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .path("data.first_name");
        });
        System.out.println("Response value from BE:" + returnedValue);
        step("Returned first name should be Janet", ()-> {
            assertThat(returnedValue, is("Janet"));
        });
    }
    @Test
    @Description("10. Non existing user data check")
    void parseJsonUserDontExist() {
        RestAssured.baseURI = baseUrl;
        step("Creating get request for single user \'/api/users/2\' ", ()-> {
            given()
                    .filter(new AllureRestAssured())
                    .log().all();
            returnedValue = get("/api/users/23")
                        .then()
                        .statusCode(404)
                        .extract()
                        .response()
                        .path("data.first_name");
        });
        System.out.println("Response value from BE:" + returnedValue);
        step("Returned first name should be NULL", ()-> {
            //assertThat(returnedValue, isEmptyOrNullString());
            assertThat(returnedValue, is(nullValue()));
        });
    }


}

