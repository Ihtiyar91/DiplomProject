package test;

import Data.DataHelper;
import Data.SqlHelper;
import Page.StartPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardTests {
    StartPage startPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SqlHelper.cleanDb();
    }

    @BeforeEach
    public void setUp() {
        startPage = open(System.getProperty("sut.url"), StartPage.class);
    }


    @Test
    public void shouldDoPayCreditCardWithStatusApproved() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getValidApprovedCardData();
        payPage.fillPayFormat(info);
        payPage.checkSuccessNotification();
        val payStatus = SqlHelper.getStatusCreditRequestEntity();
        assertEquals("APPROVED", payStatus);
    }

    @Test
    public void shouldNotPayCreditCardWithStatusDecline() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getValidDeclinedCardData();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
        val payStatus = SqlHelper.getStatusCreditRequestEntity();
        assertEquals("DECLINED", payStatus);
    }

    @Test
    public void shouldNotPayCreditCardInvalidCardNumber() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidCardNumberIfFieldAllZeros();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
    }

    @Test
    public void shouldNotPayCreditCardAnotherCardNumber() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getAnotherBankCardNumber();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
    }

    @Test
    public void shouldNotPayCreditCardNumberFieldEmpty() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getCardNumberFieldEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardFieldMonthEmpty() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getFieldMonthEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardMonthWithZeros() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidMonthWithZeros();
        payPage.fillPayFormat(info);
        payPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotPayCreditCardIrrelevantValue() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidMonthWithIrrelevantValue();
        payPage.fillPayFormat(info);
        payPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotPayCreditCardYearFieldEmpty() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getFieldYearEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardExpiredYear() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getCardWithExpiredYear();
        payPage.fillPayFormat(info);
        payPage.verifyCardExpired();
    }

    @Test
    public void shouldNotPayCreditCardYearZero() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidYearWithZero();
        payPage.fillPayFormat(info);
        payPage.verifyCardExpired();
    }

    @Test
    public void shouldNotPayCreditCardCVCFieldEmpty() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getEmptyCVCField();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardCVCOneDigits() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidCVCWithOneDigit();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardCVCZero() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidCVCWithZero();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNoPayCreditCardCyrillic() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidCyrillic();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }


    @Test
    public void shouldNotPayCreditCardOwnerSymbols() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getInvalidOwnerSymbols();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayCreditCardOwnerFieldEmpty() {
        val payPage = startPage.getPayByCreditCard();
        val info = DataHelper.getOwnerFieldEmpty();
        payPage.fillPayFormat(info);
        payPage.verifyEmptyField();
    }
}
