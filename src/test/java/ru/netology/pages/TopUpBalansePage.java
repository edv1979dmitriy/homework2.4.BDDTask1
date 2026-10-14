package ru.netology.pages;

import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TopUpBalansePage {

    public TopUpBalansePage() {
    }

    public static void transfer(String amount, String numberDebitCard) {
        $("[data-test-id='amount'] input").click();
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='amount'] input").sendKeys("DELETE");
        $("[data-test-id='amount'] input").setValue(amount);
        $("[data-test-id='from'] input").click();
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='from'] input").sendKeys("DELETE");
        $("[data-test-id='from'] input").setValue(numberDebitCard);
        $("[data-test-id='action-transfer']").click();
    }

    public static void outWithoutTransfer(String amount, String numberDebitCard) {
        $("[data-test-id='amount'] input").click();
        $("[data-test-id='amount'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='amount'] input").sendKeys("DELETE");
        $("[data-test-id='amount'] input").setValue(amount);
        $("[data-test-id='from'] input").click();
        $("[data-test-id='from'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id='from'] input").sendKeys("DELETE");
        $("[data-test-id='from'] input").setValue(numberDebitCard);
        $("[data-test-id='action-cancel']").click();
    }

    public static void returnOriginalState() {
        DashboardPage card = new DashboardPage();
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
