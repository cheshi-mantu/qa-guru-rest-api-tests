package helpers;

public class Environment {
    public static final String
        weatherApiKey = System.getProperty("weather_api_key", null),
        weatherLang = System.getProperty("weather_lang","ru"),
        cityId = System.getProperty("city_id", "524901"),
        tlgBotIdAndSecret = System.getProperty("tlg_bot", null),
        tlgChatId = System.getProperty("tlg_chat_id", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_chat_id")),
        remoteDriverUrl = System.getProperty("remote_driver_url"),
        videoStorageUrl = System.getProperty("video_storage_url");
    public static boolean
        isRemoteDriver = remoteDriverUrl != null,
        isVideoOn = videoStorageUrl != null;
}

