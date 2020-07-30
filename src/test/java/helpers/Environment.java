package helpers;

public class Environment {
    public static final String
        weatherApiKey = System.getProperty("weather_api_key", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "weather_api_key")),
        weatherLang = System.getProperty("weather_lang","ru"),
        cityId = System.getProperty("city_id", "524901"),
        tlgBotId = System.getProperty("tlg_bot_id", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_bot_id")),
        tlgBotSecret = System.getProperty("tlg_bot_secret", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_bot_secret")),
        remoteDriverUrl = System.getProperty("remote_driver_url"),
        videoStorageUrl = System.getProperty("video_storage_url");
    public static boolean
        isRemoteDriver = remoteDriverUrl != null,
        isVideoOn = videoStorageUrl != null;
}

