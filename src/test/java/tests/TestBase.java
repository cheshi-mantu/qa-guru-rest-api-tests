package tests;


import helpers.AttachmentsHelper;
import org.junit.jupiter.api.BeforeAll;

import static helpers.Environment.*;
import static helpers.LoadCredentials.getCredentialsFromJson;

public class TestBase {
    static String weatherKey;
    static String tlgBot;
    static String tlgChat;

    @BeforeAll
    public static void setUp(){

        if (weatherApiKey != null) {
            weatherKey = weatherApiKey;
        } else {
            weatherKey = getCredentialsFromJson("ApiTests.secret", "weather_api_key");
        }

        if (tlgBotIdAndSecret != null) {
            tlgBot = tlgBotIdAndSecret;
        } else {
            tlgBot = getCredentialsFromJson("ApiTests.secret", "tlg_bot");
        }

        if (tlgChatId != null) {
            tlgChat = tlgChatId;
        } else {
            tlgChat = getCredentialsFromJson("ApiTests.secret", "tlg_chat_id");
        }
        AttachmentsHelper.attachAsText("Weather key: ", weatherKey);
        AttachmentsHelper.attachAsText("Weather key: ", tlgBot);
        AttachmentsHelper.attachAsText("Weather key: ", tlgChat);
    }
}