package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement fromCard = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement textError = $("body");
    DashboardPage dashboardPage = new DashboardPage();

    public void transferToCard(String cardNumber, int sumOfTransfer) {
        amount.setValue(String.valueOf(sumOfTransfer));
        if (cardNumber.equals("5559 0000 0000 0001")) {
            fromCard.setValue("5559 0000 0000 0002");
        } else {
            fromCard.setValue("5559 0000 0000 0001");
        }
        transferButton.click();
    }

    public SelenideElement error() {
        textError.shouldHave(text("Недостаточный баланс для перевода"));
        return textError.shouldHave(text("Недостаточный баланс для перевода"));
    }
}