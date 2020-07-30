package helpers;

public class Environment {
    public static final String
        weatherApiKey = System.getProperty("weather_api_key", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "weather_api_key")),
        weatherLang = System.getProperty("weather_lang","ru"),
        cityId = System.getProperty("city_id", "524901"),
        tlgBotIdAndSecret = System.getProperty("tlg_bot", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_bot")),
        tlgChatlId = System.getProperty("tlg_channel_id", LoadCredentials.getCredentialsFromJson("ApiTests.secret", "tlg_channel_id")),
        remoteDriverUrl = System.getProperty("remote_driver_url"),
        videoStorageUrl = System.getProperty("video_storage_url");
    public static boolean
        isRemoteDriver = remoteDriverUrl != null,
        isVideoOn = videoStorageUrl != null;
}

