package test;

import com.codeborne.selenide.Condition;
import data.DataHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferNegativeTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    public void setUpClose() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int card1Balance = dashboardPage.getCardBalance(0);
        int sumOfTransfer = 10000 - card1Balance;
        if (sumOfTransfer <= 0) {
            var transferPage = dashboardPage.choosingCardForTransfer(1);
            transferPage.transferToCard("5559 0000 0000 0001", sumOfTransfer);
        } else {
            var transferPage = dashboardPage.choosingCardForTransfer(0);
            transferPage.transferToCard("5559 0000 0000 0002", sumOfTransfer);
        }
    }

    @Test
    void shouldTransferMoneyToFirstCardOverBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(0);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        var transferPage = dashboardPage.choosingCardForTransfer(0);
        int sumOfTransfer = 20000;
        String cardFullNumber = DataHelper.getCardFullNumber(1);
        transferPage.transferToCard(cardFullNumber, sumOfTransfer);
        var card1BalanceAfterTransfer = dashboardPage.getCardBalance(0);
        var card2BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        assertEquals(card1BalanceBeforeTransfer, card1BalanceAfterTransfer);
        assertEquals(card2BalanceBeforeTransfer, card2BalanceAfterTransfer);
        transferPage.error().shouldBe(visible);
    }

    @Test
    void shouldTransferMoneyToSecondCardOverBalance() {
        DashboardPage dashboardPage = new DashboardPage();
        int card1BalanceBeforeTransfer = dashboardPage.getCardBalance(0);
        int card2BalanceBeforeTransfer = dashboardPage.getCardBalance(1);
        var transferPage = dashboardPage.choosingCardForTransfer(1);
        int sumOfTransfer = 20000;
        String cardFullNumber = DataHelper.getCardFullNumber(0);
        transferPage.transferToCard(cardFullNumber, sumOfTransfer);
        var card1BalanceAfterTransfer = dashboardPage.getCardBalance(0);
        var card2BalanceAfterTransfer = dashboardPage.getCardBalance(1);
        assertEquals(card1BalanceBeforeTransfer, card1BalanceAfterTransfer);
        assertEquals(card2BalanceBeforeTransfer, card2BalanceAfterTransfer);
        transferPage.error().shouldBe(visible);
    }
}
