package helpers;

import io.qameta.allure.Attachment;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public class AttachmentsHelper {

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

}
