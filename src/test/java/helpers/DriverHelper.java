package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import drivers.CustomWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;


import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;


public class DriverHelper {

    public static void configureSelenide() {
        Configuration.browser = CustomWebDriver.class.getName();
//        Configuration.baseUrl = webUrl;
        Configuration.timeout = 10000;
    }

    public static By byTestId(String testId) {
            return by("data-testid",  testId);
    }

    public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid","");
    }

    public static String getConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }

}