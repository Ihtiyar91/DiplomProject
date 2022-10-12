package Data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getEmptyCardNumberField() {
        return "";
    }




    public static String getInvalidCardNumber() {
        return "0000 0000 0000 0000";
    }

    public static String getAnotherBankFieldCardNumber() {
        return "4444 4444 4444 4443";
    }

    //  Month

    public static String getShiftedMonthFromNow(int shiftedMonth) {
        return LocalDate.now().plusMonths(shiftedMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth() {
        return "13";
    }

    public static String getInvalidMonthIfFieldZeros() {
        return "00";
    }


    public static String getEmptyMonthField() {
        return "";
    }

    //  Year

    public static String getShiftedYearFromNow(int shiftedYears) {
        return LocalDate.now().plusYears(shiftedYears).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYearIfFieldZeros() {
        return "00";
    }

    public static String getIncorrectYearWithOneDigit() {
        return "0";
    }

    public static String getEmptyYearField() {
        return "";
    }

    //  Owner

    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getInvalidOwnerFieldOnCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }


    public static String getInvalidOwnerFieldWithSymbols() {
        return "!@#$%^&?*";
    }

    public static String getEmptyOwnerField() {
        return "";
    }

    //  CVC/CVV

    public static String getValidCVC() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }

    public static String getInvalidCVC() {
        return "000";
    }


    public static String getIncorrectCVCWithOneDigit() {
        return "1";
    }

    public static String getEmptyFieldCVC() {
        return "";
    }
}

