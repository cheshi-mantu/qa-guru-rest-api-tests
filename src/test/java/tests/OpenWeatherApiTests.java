package tests;

import helpers.AttachmentsHelper;
import helpers.LoadCredentials;
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
import org.junit.jupiter.api.*;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OpenWeatherApiTests extends TestBase {
    private String baseUrlWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String baseUrlTlg = "https://api.telegram.org/";
    private String apiRequest;
    private String formattedMessage = "";
    private String weatherKey, tlgBot, tlgChat;
    Response response;
    @Test
    @Order(1)
    @DisplayName("Get current for given city")
    @Description("Get current weather, extract data from ")
    void parseJsonFromApiGetRestAssuredOnly() {
        RestAssured.baseURI = baseUrlWeather;

        step("Checking System properties and setting vars", ()-> {
            if (weatherApiKey == null) {
                weatherKey = LoadCredentials.getCredentialsFromJson("ApiTests.secret", "weather_api_key");
            } else {
                weatherKey = weatherApiKey;
            }
        AttachmentsHelper.attachAsText("weatherKey: ", weatherKey);
        });

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
    @DisplayName("Send Weather data via Tlg bot to chat")
    @Description("Sending formatted weather to Tlg chat and check server response ")
    void formatResponseAndSendToTlgChat() {
        RestAssured.baseURI = baseUrlTlg;
        step("Checking System properties and setting for Telegram bot", ()-> {
            if (tlgBotIdAndSecret == null) {
                tlgBot = LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_bot");
            } else {
                tlgBot = tlgBotIdAndSecret;
            }
            if (tlgChatId == null) {
                tlgChat = LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_chat_id");
            } else {
                tlgChat = tlgChatId;
            }
            AttachmentsHelper.attachAsText("Telegram bot data: ", tlgBot);
            AttachmentsHelper.attachAsText("Telegram chat data: ", tlgChat);
        });

        step("PRER Create message for next test", ()->{
            formattedMessage = "Город: " +  response.path("name") + "\n" +
                    "Погода:" + response.path("weather[0].description") + "\n" +
                    "Температура: " + response.path("main.temp") + "\n" +
                    "Ощущается: " + response.path("main.feels_like") + "\n" +
                    "Давление: " + response.path("main.pressure") + "\n" +
                    "Ветер: " + response.path("wind.speed") + " м/с " + response.path("wind.deg");
            AttachmentsHelper.attachAsText("Message to send: ", formattedMessage);
        });

        step("PREP: Build request params", ()->{
            apiRequest = tlgBot + "/sendMessage?chat_id=" + tlgChat + "&text=" + formattedMessage;
            AttachmentsHelper.attachAsText("API response: ", response.asString());
        });

        step("ACT & Assert: Send get and assert the response is 200", ()-> {
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

