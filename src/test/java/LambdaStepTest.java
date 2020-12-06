import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class LambdaStepTest {
    private final static String URL = "https://github.com/a-pilipchuk/qa.guru_course";
    private final static String SOURCE = "lambda step test";
    Credentials creds = new Credentials();


    @DisplayName("Аннотации + лямбда-выражения")
    @Test
    public void addIssue () {
        step("Открываем репозиторий", () -> {
            open(URL);
        });
        step("Логинимся", () ->{
            $(".HeaderMenu [href*='login']").click();
            $("#login_field").setValue(creds.getLogin());
            $("#password").setValue(creds.getPassword());
            $("[value='Sign in']").click();
        });
        step("Переходим в issues", () ->{
            $("[data-content='Issues']").click();
        });
        step("Инициируем создание issue", () ->{
            $("a[data-hotkey='c']").click();
        });
        step("Заполняем заголовок", () -> {
            $("#issue_title").setValue("issue " + SOURCE);
        });
        step("Назначаем на себя", () -> {
            $(".js-issue-assign-self").click();
        });
        step("Присваиваем тег", () -> {
            $("[data-hotkey='l']").click();
            $(withText("good first issue")).click();
            $("[data-hotkey='l']").pressEscape();
        });
        step("Сохраняем issue", () -> {
            $(withText("Submit new issue")).click();
        });
        step("Проверяем, что issue создана, и закрываем браузер", () ->{
            $(".js-issue-title").shouldHave(text("issue " + SOURCE));
            Selenide.closeWindow();
        });
    }
}
