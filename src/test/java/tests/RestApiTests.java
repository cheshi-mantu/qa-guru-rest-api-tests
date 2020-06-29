package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;

@Epic("QA.GURU QA automation course")
@Story("Selenide TKB tests homeworx")
@Tag("tkb_tests")
class RestApiTests extends TestBase {

    @Test
    @Description("Open page, find Вклады button, select closest A, then click")
    @DisplayName("Open main page, click on Вклады button by closest A")
    void pageOpenButtonClickClosestA() {
        step ("Open Tinkoff main page", () -> open(url));
        step("Locate and press Вклады on page top", () -> {
            $(byText("Вклады")).closest("a").click();
            $("h1").shouldHave(text("Откройте вклад"));
        });
    }


}

