package ru.netology.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
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
    }
}
