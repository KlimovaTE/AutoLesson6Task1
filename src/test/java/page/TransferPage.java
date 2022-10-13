package page;

import com.codeborne.selenide.SelenideElement;

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
        fromCard.setValue(cardNumber);
        transferButton.click();
    }

    public SelenideElement error() {
        return textError.shouldHave(text("Недостаточный баланс для перевода"));
    }
}