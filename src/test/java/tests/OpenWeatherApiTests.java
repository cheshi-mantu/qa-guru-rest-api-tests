package tests;

import helpers.AttachmentsHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.params.CoreConnectionPNames;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("QA.GURU QA automation course")
@Feature("Work with REST API")
@Story("REST API tests with REST Assured")
@Tag("rest_api_tests_weather")
class OpenWeatherApiTests extends TestBase {
    private String baseUrl = "http://api.openweathermap.org/data/2.5/weather?";
    String errorText;
    Response response;
    RestAssuredConfig config;
    String apiRequest;
    @Test
    @DisplayName("Get current for given city")
    @Description("Get current weather, extract data from ")
    void parseJsonFromApiGetRestAssuredOnly() {
            RestAssured.baseURI = baseUrl;
            apiRequest = "?id=" + cityId + "&units=metric&lang=" + weatherLang + "&appid=" + weatherApiKey;
        step("Build get request for group of users, getting the reponse. Assert.", ()-> {
            response = given()
                    .filter(new AllureRestAssured())
                    .log().all()
                    .when()
                    .get(apiRequest);
        });
        step("Assert response", ()->{
            assertThat(response.statusCode(), is(equalTo(200)));
        });

    }



}

