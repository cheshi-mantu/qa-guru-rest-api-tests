package tests;


import org.junit.jupiter.api.BeforeAll;

import static helpers.Environment.*;
import static helpers.LoadCredentials.getCredentialsFromJson;

public class TestBase {
    static String weatherKey;
    static String tlgBot;
    static String tlgChat;

    @BeforeAll
    public static void setUp(){

        if (weatherApiKey == null) {
            weatherKey = getCredentialsFromJson("ApiTests.secret", "weather_api_key");
        } else {
            weatherKey = weatherApiKey;
        }
        if (tlgBotIdAndSecret == null) {
            tlgBot = getCredentialsFromJson("ApiTests.secret", "tlg_bot");
        } else {
            tlgBot = tlgBotIdAndSecret;
        }
        if (tlgChatId == null) {
            tlgChat = getCredentialsFromJson("ApiTests.secret", "tlg_chat_id");
        } else {
            tlgChat = tlgChatId;
        }


    }
}