package tests;

import helpers.AttachmentsHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static helpers.GetDate.getTodaysDate;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static io.qameta.allure.Allure.parameter;

@Feature("Send USD Fx rate")
@Tag("rest_api_tests_fx")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Owner("egorivanov")
class CbrRuUsdFxRateTests extends TestBase {
    private String baseUrlCbr = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private String baseUrlTlg = "https://api.telegram.org/";
    private String apiReqPath;
    private String apiRequest;
    private String formattedMessage = "";

    Response response;

    @Test
    @Order(1)
    @DisplayName("Get USD Fx rate from cbr.ru, store in response, check response status")
    @Description("Send API request for USD Fx rate, store in response, check the status is 200")
    void getUSDFxRateFromCbrTest() {
    parameter("baseUrlCbr", baseUrlCbr);
        RestAssured.baseURI = baseUrlCbr;
        apiReqPath = getTodaysDate();

        step("Building apiRequest string", ()->{
            apiRequest = baseUrlCbr + apiReqPath;
            AttachmentsHelper.attachAsText("apiRequest: ", apiRequest);
            registerParser("application/xml", Parser.XML);
        });

        step("Build get request for the given date", ()-> {
            response = given()
                    .filter(new AllureRestAssured())
                    .log().all()
                    .when()
                    .get(apiRequest)
                    .then()
                    .contentType(ContentType.XML)
                    .extract().response();
            AttachmentsHelper.attachAsText("API response: ", response.prettyPrint());
        });
        step("Assert response", ()->{
            assertThat(response.statusCode(), is(equalTo(200)));
        });
    }

    @Test
    @Order(2)
    @DisplayName("Extract Fx rate for USD, create message and send")
    @Description("Extract USD Fx rate, prep. the message, send the message to Tlg chat, check response")
    void formatCBRResponseAndSendToTlgChat() {
        parameter("apiResponse", "see attachment");
        parameter("tlgBot", "hidden value");
        parameter("tlgChat", "hidden value");

        RestAssured.baseURI = baseUrlTlg;

        step("Attaching API response from previous step",()->{
            AttachmentsHelper.attachAsText("apiResponse", response.asString());
            AttachmentsHelper.attachAsText("baseURI", baseUrlTlg);
        });
        step("Parsing the response from previous test and creating the string to send", ()->{
            XmlPath xmlpath = new XmlPath(response.asString());
            String charCodeValue = xmlpath.get("ValCurs.Valute.find { it.CharCode == 'USD' }.Value");
            System.out.println("RESPONSE: " + charCodeValue);
            formattedMessage = "USD FX rate on "+ apiReqPath + " is " + charCodeValue;
            AttachmentsHelper.attachAsText("Returned USD FX rate: ", charCodeValue);
        });

        step("PREP: Create message for next test", ()->{
            AttachmentsHelper.attachAsText("Message to send: ", formattedMessage);
        });

        step("PREP: Build request params for tlg bot", ()->{
            apiRequest = tlgBot + "/sendMessage?chat_id=" + tlgChat + "&text=" + formattedMessage;
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
