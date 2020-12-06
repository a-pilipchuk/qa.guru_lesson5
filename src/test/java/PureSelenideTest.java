import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class PureSelenideTest {
    private final static String URL = "https://github.com/a-pilipchuk/qa.guru_course";
    private final static String SOURCE = "pure selenide test";
    Credentials creds = new Credentials();

    @DisplayName("Простой тест")
    @Test
    public void addIssue () {
        open(URL);
        $(".HeaderMenu [href*='login']").click();
        $("#login_field").setValue(creds.getLogin());
        $("#password").setValue(creds.getPassword());
        $("[value='Sign in']").click();
        $("[data-content='Issues']").click();
        $("a[data-hotkey='c']").click();
        $("#issue_title").setValue("issue " + SOURCE);
        $(".js-issue-assign-self").click();
        $("[data-hotkey='l']").click();
        $(withText("good first issue")).click();
        $("[data-hotkey='l']").pressEscape();
        $(withText("Submit new issue")).click();
        $(".js-issue-title").shouldHave(text("issue " + SOURCE));
        Selenide.closeWindow();
    }
}
