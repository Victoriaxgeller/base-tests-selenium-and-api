package ui;

import browser.Browser;
import listener.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class BaseTest {

    public static final String BASE_URL = "https://www.olx.ua/uk/";
    protected WebDriver driver;
    protected Browser browser;

    @BeforeClass
    public void setup() {
        browser = new Browser();
        driver = browser.setup().getDriver();

    }

    @BeforeMethod
    public void startTest() {
        browser.openURL(BASE_URL);
        closeInfoBar();
        browser.refresh();
    }

    @AfterClass
    public void close() {
        driver.quit();
    }


    protected void closeInfoBar() {
        browser.setCookie("cookieBarSeen", "true");
    }
}
