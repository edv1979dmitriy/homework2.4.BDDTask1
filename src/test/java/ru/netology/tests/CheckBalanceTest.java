package ru.netology.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.AuthorizationPage;
import ru.netology.pages.DashboardPage;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CheckBalanceTest {

    DashboardPage dashboardPage;

    @BeforeEach
    public void successfulLoginAccountTest() {
        var authorizationPage = Selenide.open("http://localhost:9999", AuthorizationPage.class);
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var verificationField = authorizationPage.loginOfPersonalAccount(info);
        this.dashboardPage = verificationField.verification(verificationCode);
        dashboardPage.checkPageVisible();
    }

    @AfterEach
    public void returnOriginalState() {
        DashboardPage card = new DashboardPage();
        int difference1 = DataHelper.getCardInfo(0).getInitialbalance() - card.getCardBalance(0);
        int difference2 = DataHelper.getCardInfo(1).getInitialbalance() - card.getCardBalance(1);
        if (difference1 >= 0) {
            var topUpBalanse = card.chooseCard(DataHelper.getCardInfo(0));
            topUpBalanse.transfer(Integer.toString(difference1), DataHelper.getCardInfo(1).getNamberCard());
        }
        if (difference1 < 0) {
            var topUpBalanse = card.chooseCard(DataHelper.getCardInfo(1));
            topUpBalanse.transfer(Integer.toString(difference2), DataHelper.getCardInfo(0).getNamberCard());
        }
        $("[data-test-id='action-reload']").click();
    }

    @Test
    @DisplayName("Should top up first card from second card amount valid")
    public void shouldTopUpFirstCardFromSecondCardAmountValid() {
        int amount = 1000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() + amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() - amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up first card from second card amount equals balanse")
    public void shouldTopUpFirstCardFromSecondCardAmountEqualsBalanse() {
        int amount = 10000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() + amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() - amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up first card from second card amount zero")
    public void shouldTopUpFirstCardFromSecondCardAmountZero() {
        int amount = 0;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() + amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() - amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up first card from second card amount negative")
    public void shouldTopUpFirstCardFromSecondCardAmountNegative() {
        int amount = -1;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() + Math.abs(amount);
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() - Math.abs(amount);
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up first card from second card amount bigger balance")
    public void shouldTopUpFirstCardFromSecondCardAmountBiggerBalance() {
        int amount = 10001;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance),
                () -> topUpBalanse.errorMessage("Ошибка! ")
        );
    }

    @Test
    @DisplayName("Should top up second card from first card amount valid")
    public void shouldTopUpSecondCardFromFirstCardAmountValide() {
        int amount = 1000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() - amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() + amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up second card from first card amount equals balance")
    public void shouldTopUpSecondCardFromFirstCardAmountEqualsBalanse() {
        int amount = 10000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() - amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() + amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up second card from first card amount zero")
    public void shouldTopUpSecondCardFromFirstCardAmountZero() {
        int amount = 0;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() - amount;
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() + amount;
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should top up second card from first card amount negativ")
    public void shouldTopUpSecondCardFromFirstCardAmountNegativ() {
        int amount = -1;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance() - Math.abs(amount);
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance() + Math.abs(amount);
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }


    @Test
    @DisplayName("Should top up second card from first card amount bigger balance")
    public void shouldTopUpSecondCardFromFirstCardAmountBiggerBalanse() {
        int amount = 10001;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance),
                () -> topUpBalanse.errorMessage("Ошибка! ")
        );
    }

    @Test
    @DisplayName("Should out without transfer first card")
    public void shouldOutWithoutTransferCard1() {
        int amount = 3333;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.outWithoutTransfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should out without transfer second card")
    public void shouldOutWithoutTransferCard2() {
        int amount = 3333;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.outWithoutTransfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        dashboardPage.checkPageVisible();
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance)
        );
    }

    @Test
    @DisplayName("Should show error if incorrectly numder first card")
    public void shouldShowErrorIfIncorrectlyNumderCard1() {
        int amount = 1000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(1));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(1).getNamberCard());
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance),
                () -> topUpBalanse.errorMessage("Ошибка! ")
        );
    }

    @Test
    @DisplayName("Should show error if incorrectly numder second card")
    public void shouldShowErrorIfIncorrectlyNumderCard2() {
        int amount = 1000;
        var topUpBalanse = dashboardPage.chooseCard(DataHelper.getCardInfo(0));
        topUpBalanse.transfer(String.valueOf(amount), DataHelper.getCardInfo(0).getNamberCard());
        var expectedFirstCardBalance = DataHelper.getCardInfo(0).getInitialbalance();
        var expectedSecondCardBalance = DataHelper.getCardInfo(1).getInitialbalance();
        assertAll(
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(0), expectedFirstCardBalance),
                () -> dashboardPage.checkCardBalance(DataHelper.getCardInfo(1), expectedSecondCardBalance),
                () -> topUpBalanse.errorMessage("Ошибка! ")
        );
    }
}
