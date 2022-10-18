package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private final SelenideElement heading = $("h2.heading");
    private final SelenideElement buyButton = $$(".button").first(); // $(byText("Купить"));
    private final SelenideElement buyButtonOnCredit = $$(".button").last(); // $(byText("Купить в кредит"));

    private final SelenideElement payByCard = $$("h3.heading").find(Condition.exactText("Оплата по карте"));
    private final SelenideElement creditCardData = $$("h3.heading").find(Condition.exactText("Кредит по данным карты"));

    public StartPage() {
        heading.shouldBe(Condition.visible);
    }

    public PayPage getDebitCardPay() {
        buyButton.click();
        payByCard.shouldBe(Condition.visible);
        return new PayPage();
    }

    public PayPage getPayByCreditCard() {
        buyButtonOnCredit.click();
        creditCardData.shouldBe(Condition.visible);
        return new PayPage();
    }
}
