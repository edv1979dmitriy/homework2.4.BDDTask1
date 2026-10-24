package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
    }

    public void checkPageVisible() {
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
    }

    public int getCardBalance(int index) {
        var card = cards.get(index).text();
        var start = card.indexOf(balanceStart);
        var finish = card.indexOf(balanceFinish);
        var value = card.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void chooseCard(int index) {
        cards.get(index).$("[data-test-id='action-deposit']").click();
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(visible, Duration.ofSeconds(10));
    }
}
