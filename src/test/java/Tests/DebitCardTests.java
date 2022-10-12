package Tests;

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
}
