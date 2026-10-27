package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpBalansePage {

    public TopUpBalansePage() {
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(visible, Duration.ofSeconds(10));
    }

    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement numberCardField = $("[data-test-id='from'] input");

    public void fillInFields(String amount, String numberDebitCard) {
        amountField.click();
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        amountField.sendKeys("DELETE");
        amountField.setValue(amount);
        numberCardField.click();
        numberCardField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        numberCardField.sendKeys("DELETE");
        numberCardField.setValue(numberDebitCard);
    }

    public void transfer(String amount, String numberDebitCard) {
        fillInFields(amount, numberDebitCard);
        $("[data-test-id='action-transfer']").click();
    }

    public void outWithoutTransfer(String amount, String numberDebitCard) {
        fillInFields(amount, numberDebitCard);
        $("[data-test-id='action-cancel']").click();
    }

    public void errorMessage(String error) {
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text(error));
    }
}
