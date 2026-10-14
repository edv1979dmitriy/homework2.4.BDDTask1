package ru.netology.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import ru.netology.pages.DashboardPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.pages.AuthorizationPage.*;
import static ru.netology.pages.TopUpBalansePage.*;
import static ru.netology.pages.VerificationPage.verification;

public class CheckBalanceTest {

    String card1 = "5559 0000 0000 0001";
    String card2 = "5559 0000 0000 0002";
    DashboardPage card = new DashboardPage();

    @BeforeAll
    public static void successfulLoginAccountTest(){
        loginOfPersonalAccount();
        $("h2").shouldHave(text("Интернет Банк")).shouldBe(visible);
        verification();
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible);
    }

    @AfterEach
    public void returnState() {
        returnOriginalState();
    }


    @Test
    @DisplayName("Should top up first card from second card amount valid")
    public void shouldTopUpFirstCardFromSecondCardAmountValid() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("1000", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(11_000, card.getCardBalance(0));
        Assertions.assertEquals(9_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount equals balanse")
    public void shouldTopUpFirstCardFromSecondCardAmountEqualsBalanse() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("10000", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(20_000, card.getCardBalance(0));
        Assertions.assertEquals(0, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount zero")
    public void shouldTopUpFirstCardFromSecondCardAmountZero() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("0", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount negative")
    public void shouldTopUpFirstCardFromSecondCardAmountNegative() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("-1", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_001, card.getCardBalance(0));
        Assertions.assertEquals(9_999, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount bigger balance")
    public void shouldTopUpFirstCardFromSecondCardAmountBiggerBalance() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("10001", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount valid")
    public void shouldTopUpSecondCardFromFirstCardAmountValide() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("1000", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(9_000, card.getCardBalance(0));
        Assertions.assertEquals(11_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount equals balance")
    public void shouldTopUpSecondCardFromFirstCardAmountEqualsBalanse() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("10000", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(0, card.getCardBalance(0));
        Assertions.assertEquals(20_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount zero")
    public void shouldTopUpSecondCardFromFirstCardAmountZero() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("0", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount negativ")
    public void shouldTopUpSecondCardFromFirstCardAmountNegativ() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("-1", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(9_999, card.getCardBalance(0));
        Assertions.assertEquals(10_001, card.getCardBalance(1));
    }


    @Test
    @DisplayName("Should top up second card from first card amount bigger balance")
    public void shouldTopUpSecondCardFromFirstCardAmountBiggerBalanse() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        $("h1").shouldHave(Condition.text("Пополнение карты")).shouldBe(Condition.visible, Duration.ofSeconds(10));
        transfer("10001", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should out without transfer first card")
    public void shouldOutWithoutTransferCard1() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        outWithoutTransfer("3333", card2);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should out without transfer second card")
    public void shouldOutWithoutTransferCard2() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        outWithoutTransfer("3333", card1);
        $("h1").shouldHave(text("Ваши карты")).shouldBe(visible, Duration.ofSeconds(10));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should show error if incorrectly numder first card")
    public void shouldShowErrorIfIncorrectlyNumderCard1() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(1);
        transfer("1000", card2);
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! "));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should show error if incorrectly numder second card")
    public void shouldShowErrorIfIncorrectlyNumderCard2() {
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        card.chooseCard(0);
        transfer("1000", card1);
        $("[data-test-id='error-notification']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text("Ошибка! "));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

}
