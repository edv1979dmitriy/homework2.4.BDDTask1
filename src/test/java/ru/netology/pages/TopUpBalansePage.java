package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TopUpBalansePage {

    public TopUpBalansePage() {
    }

    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement numberCardField = $("[data-test-id='from'] input");

    public void checkPageVisible() {
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(visible, Duration.ofSeconds(10));
    }

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

    public void returnOriginalState() {
        DashboardPage card = new DashboardPage();
        int difference1 = DataHelper.getCardInfo(0).getInitialbalance() - card.getCardBalance(0);
        int difference2 = DataHelper.getCardInfo(1).getInitialbalance() - card.getCardBalance(1);
        if (difference1 >= 0) {
            card.chooseCard(DataHelper.getCardInfo(0));
            transfer(Integer.toString(difference1), DataHelper.getCardInfo(1).getNamberCard());
        }
        if (difference1 < 0) {
            card.chooseCard(DataHelper.getCardInfo(1));
            transfer(Integer.toString(difference2), DataHelper.getCardInfo(0).getNamberCard());
        }
        $("[data-test-id='action-reload']").click();
    }
}
