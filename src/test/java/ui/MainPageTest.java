package ui;

import listener.TestListener;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MainPage;

public class MainPageTest extends BaseTest {


    private MainPage mainPage;

    @Test(dataProvider = "categories", testName = "Check category on the main page is clickable")
    public void checkCategoryIsClickable(String categoryName, String categoryText) {
        mainPage = new MainPage(driver);
        mainPage.clickOnCategory(categoryName);
        String s = mainPage.getTextFormLookThroughAllAddsLink();

        Assert.assertEquals(s, categoryText);

    }

    @DataProvider(name = "categories")
    public static Object[][] categories() {
        return new Object[][]{
//                {"Авто", "Переглянути всі оголошення в Авто"},
                {"Нерухомість", "Переглянути всі оголошення в Нерухомість"},
        };
    }
}
