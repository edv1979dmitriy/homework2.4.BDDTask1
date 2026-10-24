package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpBalansePage {

    public TopUpBalansePage() {
    }

    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement numberCardField = $("[data-test-id='from'] input");

    public void transfer(String amount, String numberDebitCard) {
        amountField.click();
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        amountField.sendKeys("DELETE");
        amountField.setValue(amount);
        numberCardField.click();
        numberCardField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        numberCardField.sendKeys("DELETE");
        numberCardField.setValue(numberDebitCard);
        $("[data-test-id='action-transfer']").click();
    }

    public void outWithoutTransfer(String amount, String numberDebitCard) {
        amountField.click();
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        amountField.sendKeys("DELETE");
        amountField.setValue(amount);
        numberCardField.click();
        numberCardField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        numberCardField.sendKeys("DELETE");
        numberCardField.setValue(numberDebitCard);
        $("[data-test-id='action-cancel']").click();
    }

    public void errorMessage() {
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! "));
    }

    public void returnOriginalState(DashboardPage card) {
        int difference1 = 10_000 - card.getCardBalance(0);
        int difference2 = 10_000 - card.getCardBalance(1);
        if (difference1 >= 0) {
            card.chooseCard(0);
            transfer(Integer.toString(difference1), "5559 0000 0000 0002");
        }
        if (difference1 < 0) {
            card.chooseCard(1);
            transfer(Integer.toString(difference2), "5559 0000 0000 0001");
        }
        $("[data-test-id='action-reload']").click();
    }
}
