package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement codeButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
        codeField.shouldBe(visible, Duration.ofSeconds(10));
    }

    public DashboardPage verification(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCodeSMS());
        codeButton.click();
        return new DashboardPage();
    }
}