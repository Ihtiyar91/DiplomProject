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

public class DebitCardTests {
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
    public void shouldPayDebitCardWithStatusApproved() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getValidApprovedCardData();
        payPage.fillPayFormat(info);
        payPage.checkSuccessNotification();
        val payStatus = SqlHelper.getStatusPayEntity();
        assertEquals("APPROVED", payStatus);
    }
    @Test
    public void shouldNotPayDebitCardWithStatusDecline() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getValidDeclinedCardData();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
        val payStatus = SqlHelper.getStatusPayEntity();
        assertEquals("DECLINED", payStatus);
    }
    @Test
    public void shouldNotPayDebitCardInvalidCardNumber() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidCardNumberIfFieldAllZeros();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
    }

    @Test
    public void shouldNotPayDebitCardAnotherCardNumber() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getAnotherBankCardNumber();
        payPage.fillPayFormat(info);
        payPage.checkErrorNotification();
    }

    @Test
    public void shouldNotPayDebitCardNumberFieldEmpty() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getCardNumberFieldEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardFieldMonthEmpty() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getFieldMonthEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardMonthWithZeros() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidMonthWithZeros();
        payPage.fillPayFormat(info);
        payPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotPayDebitCardIrrelevantValue() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidMonthWithIrrelevantValue();
        payPage.fillPayFormat(info);
        payPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotPayDebitCardYearFieldEmpty() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getFieldYearEmpty();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardExpiredYear() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getCardWithExpiredYear();
        payPage.fillPayFormat(info);
        payPage.verifyCardExpired();
    }

    @Test
    public void shouldNotPayDebitCardYearZero() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidYearWithZero();
        payPage.fillPayFormat(info);
        payPage.verifyCardExpired();
    }

    @Test
    public void shouldNotPayDebitCardCVCFieldEmpty() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getEmptyCVCField();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardCVCOneDigits() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidCVCWithOneDigit();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardCVCZero() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidCVCWithZero();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNoPayDebitCardCyrillic() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidCyrillic();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }


    @Test
    public void shouldNotPayDebitCardOwnerSymbols() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getInvalidOwnerSymbols();
        payPage.fillPayFormat(info);
        payPage.checkWrongFormat();
    }

    @Test
    public void shouldNotPayDebitCardOwnerFieldEmpty() {
        val payPage = startPage.getDebitCardPay();
        val info = DataHelper.getOwnerFieldEmpty();
        payPage.fillPayFormat(info);
        payPage.verifyEmptyField();
    }
}
