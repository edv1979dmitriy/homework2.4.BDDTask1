package ru.netology.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.AuthorizationPage;
import ru.netology.pages.DashboardPage;
import ru.netology.pages.TopUpBalansePage;

public class CheckBalanceTest {

    String card1 = "5559 0000 0000 0001";
    String card2 = "5559 0000 0000 0002";
    TopUpBalansePage topUpBalanse = new TopUpBalansePage();
    DashboardPage card = new DashboardPage();

    @BeforeAll
    public static void successfulLoginAccountTest(){
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var authorizationPage = Selenide.open("http://localhost:9999", AuthorizationPage.class);
        var verificationField = authorizationPage.loginOfPersonalAccount(info);
        var dashboardPage = verificationField.verification(verificationCode);
        dashboardPage.checkPageVisible();
    }

    @AfterEach
    public void returnState() {
        topUpBalanse.returnOriginalState(card);
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount valid")
    public void shouldTopUpFirstCardFromSecondCardAmountValid() {
        card.chooseCard(0);
        topUpBalanse.transfer("1000", card2);
        card.checkPageVisible();
        Assertions.assertEquals(11_000, card.getCardBalance(0));
        Assertions.assertEquals(9_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount equals balanse")
    public void shouldTopUpFirstCardFromSecondCardAmountEqualsBalanse() {
        card.chooseCard(0);
        topUpBalanse.transfer("10000", card2);
        card.checkPageVisible();
        Assertions.assertEquals(20_000, card.getCardBalance(0));
        Assertions.assertEquals(0, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount zero")
    public void shouldTopUpFirstCardFromSecondCardAmountZero() {
        card.chooseCard(0);
        topUpBalanse.transfer("0", card2);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount negative")
    public void shouldTopUpFirstCardFromSecondCardAmountNegative() {
        card.chooseCard(0);
        topUpBalanse.transfer("-1", card2);
        card.checkPageVisible();
        Assertions.assertEquals(10_001, card.getCardBalance(0));
        Assertions.assertEquals(9_999, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up first card from second card amount bigger balance")
    public void shouldTopUpFirstCardFromSecondCardAmountBiggerBalance() {
        card.chooseCard(0);
        topUpBalanse.transfer("10001", card2);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(1));
        Assertions.assertEquals(10_000, card.getCardBalance(0));
    }

    @Test
    @DisplayName("Should top up second card from first card amount valid")
    public void shouldTopUpSecondCardFromFirstCardAmountValide() {
        card.chooseCard(1);
        topUpBalanse.transfer("1000", card1);
        card.checkPageVisible();
        Assertions.assertEquals(9_000, card.getCardBalance(0));
        Assertions.assertEquals(11_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount equals balance")
    public void shouldTopUpSecondCardFromFirstCardAmountEqualsBalanse() {
        card.chooseCard(1);
        topUpBalanse.transfer("10000", card1);
        card.checkPageVisible();
        Assertions.assertEquals(0, card.getCardBalance(0));
        Assertions.assertEquals(20_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount zero")
    public void shouldTopUpSecondCardFromFirstCardAmountZero() {
        card.chooseCard(1);
        topUpBalanse.transfer("0", card1);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should top up second card from first card amount negativ")
    public void shouldTopUpSecondCardFromFirstCardAmountNegativ() {
        card.chooseCard(1);
        topUpBalanse.transfer("-1", card1);
        card.checkPageVisible();
        Assertions.assertEquals(9_999, card.getCardBalance(0));
        Assertions.assertEquals(10_001, card.getCardBalance(1));
    }


    @Test
    @DisplayName("Should top up second card from first card amount bigger balance")
    public void shouldTopUpSecondCardFromFirstCardAmountBiggerBalanse() {
        card.chooseCard(1);
        topUpBalanse.transfer("10001", card1);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should out without transfer first card")
    public void shouldOutWithoutTransferCard1() {
        card.chooseCard(0);
        topUpBalanse.outWithoutTransfer("3333", card2);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should out without transfer second card")
    public void shouldOutWithoutTransferCard2() {
        card.chooseCard(1);
        topUpBalanse.outWithoutTransfer("3333", card1);
        card.checkPageVisible();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should show error if incorrectly numder first card")
    public void shouldShowErrorIfIncorrectlyNumderCard1() {
        card.chooseCard(1);
        topUpBalanse.transfer("1000", card2);
        topUpBalanse.errorMessage();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

    @Test
    @DisplayName("Should show error if incorrectly numder second card")
    public void shouldShowErrorIfIncorrectlyNumderCard2() {
        card.chooseCard(0);
        topUpBalanse.transfer("1000", card1);
        topUpBalanse.errorMessage();
        Assertions.assertEquals(10_000, card.getCardBalance(0));
        Assertions.assertEquals(10_000, card.getCardBalance(1));
    }

}
