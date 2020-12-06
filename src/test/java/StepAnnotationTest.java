import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class StepAnnotationTest {
    private final static String SOURCE = "step annotation test";
    private final static String URL = "https://github.com/a-pilipchuk/qa.guru_course";

    @Test
    @DisplayName("Тест с аннотациями")
    @Feature("Issues")
    @Story("User should be able to create an issue")
    public void addIssue () {
        final BaseSteps steps = new BaseSteps();
        steps.openRepo(URL);
        steps.doLogin();
        steps.openIssuesPage();
        steps.saveNewIssue(SOURCE);
        steps.shouldBeIssueTitle(SOURCE);
        Selenide.closeWindow();
    }

    public static class BaseSteps {
        @Step("Открываем репозиторий ${URL}")
        public void openRepo(final String link) {open(link);}

        @Step("Логинимся")
        public void doLogin() {
            Credentials creds = new Credentials();
            $(".HeaderMenu [href*='login']").click();
            $("#login_field").setValue(creds.getLogin());
            $("#password").setValue(creds.getPassword());
            $("[value='Sign in']").click();
        }

        @Step("Переходим на страницу Issues")
        public void openIssuesPage() {
            $("[data-content='Issues']").click();
        }

        @Step("Создаём и сохраняем issue")
        public void saveNewIssue(final String source) {
            $("a[data-hotkey='c']").click();
            $("#issue_title").setValue("issue " + source);
            $(".js-issue-assign-self").click();
            $("[data-hotkey='l']").click();
            $(withText("good first issue")).click();
            $("[data-hotkey='l']").pressEscape();
            $(withText("Submit new issue")).click();
        }

        @Step("Проверяем заголовок сохранённого issue")
        public void shouldBeIssueTitle(final String source) {
            $(".js-issue-title").shouldHave(text("issue " + source));
        }
    }
}
