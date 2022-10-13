package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection replenishButtons = $$("[data-test-id=action-deposit]");


    public DashboardPage() {
    }


    public int getCardBalance(int cardNumber) {
        val text = cards.get(cardNumber).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage choosingCardForTransfer(int numberCard) {
        SelenideElement replenishButton = replenishButtons.get(numberCard);
        replenishButton.click();
        return new TransferPage();
    }
}
