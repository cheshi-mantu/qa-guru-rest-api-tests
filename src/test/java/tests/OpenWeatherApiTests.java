package tests;

import helpers.AttachmentsHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static helpers.Environment.*;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@Epic("Day-to-day stuff automation")
@Feature("Send weather forecast")
@Tag("rest_api_tests_weather")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenWeatherApiTests extends TestBase {
    private String baseUrlWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String baseUrlTlg = "https://api.telegram.org/";
    private String apiRequest;
    private String formattedMessage = "";

    Response response;
    @Test
    @Order(1)
    @Description("Get current weather, store in response, check status is 200 ")
    void getWeatherForecastTest() {
        parameter("baseUrlWeather", baseUrlWeather);
        parameter("cityId", cityId);
        RestAssured.baseURI = baseUrlWeather;

        step("Building apiRequest string", ()->{
            apiRequest = "?id=" + cityId + "&units=metric&lang=" + weatherLang + "&appid=" + weatherKey;
            AttachmentsHelper.attachAsText("apiRequest: ", apiRequest);
        });

        step("Build get request for group of users, getting the reponse. Assert the response", ()-> {
            response = given()
                    .filter(new AllureRestAssured())
                    .log().all()
                    .when()
                    .get(apiRequest);
            AttachmentsHelper.attachAsText("API response: ", response.asString());
        });
        step("Assert response", ()->{
            assertThat(response.statusCode(), is(equalTo(200)));
        });
    }

    @Test
    @Order(2)
    @Description("Sending formatted weather to Tlg chat and check server response ")
    void formatWeatherResponseAndSendToTlgChat() {
        RestAssured.baseURI = baseUrlTlg;
//        step("Sending request data as attach to the test results", ()-> {
//            AttachmentsHelper.attachAsText("Telegram bot data: ", tlgBot);
//            AttachmentsHelper.attachAsText("Telegram chat data: ", tlgChat);
//
//        });

        step("PREP: Create message for next test", ()->{
            formattedMessage = "Город: " +  response.path("name") + "\n" +
                    "Погода: " + response.path("weather[0].description") + "\n" +
                    "Температура: " + response.path("main.temp") + "\n" +
                    "Ощущается: " + response.path("main.feels_like") + "\n" +
                    "Давление: " + response.path("main.pressure") + "\n" +
                    "Ветер: " + response.path("wind.speed") + " м/с " + response.path("wind.deg");
            AttachmentsHelper.attachAsText("Message to send: ", formattedMessage);
        });

        step("PREP: Build request params for tlg bot", ()->{
            apiRequest = tlgBot + "/sendMessage?chat_id=" + tlgChat + "&text=" + formattedMessage;
            AttachmentsHelper.attachAsText("API response: ", response.asString());
        });

        step("ACT & Assert: send get and assert the response is 200", ()-> {
                given()
                .filter(new AllureRestAssured())
                .log().all()
                .when()
                .get(apiRequest)
                .then()
                .statusCode(200);
        });
    }

}
