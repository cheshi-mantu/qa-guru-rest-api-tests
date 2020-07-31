package helpers;
import static helpers.LoadCredentials.getCredentialsFromJson;
public class Environment {
    public static final String
        weatherApiKey = System.getProperty("weather_api_key", getCredentialsFromJson("ApiTests.secret", "weather_api_key")),
        weatherLang = System.getProperty("weather_lang","ru"),
        cityId = System.getProperty("city_id", "524901"),
        tlgBotIdAndSecret = System.getProperty("tlg_bot", getCredentialsFromJson("ApiTests.secret", "tlg_bot")),
        tlgChatId = System.getProperty("tlg_chat_id", getCredentialsFromJson("ApiTests.secret", "tlg_chat_id"));
}

