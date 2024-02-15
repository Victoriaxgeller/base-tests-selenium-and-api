package browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Browser {

    WebDriver driver;

    public WebDriver getDriver() {
        if (driver == null) {
            setup();
        }
        return driver;
    }


    public Browser setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("disable-infobars");
        driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        WebDriverContext.setDriver(driver);
        return this;
    }

    public void setupHeadless() {


    }

    public void openURL(String URL) {
        driver.get(URL);

    }

    public void setCookie(String name, String value) {
        getDriver().manage().addCookie(new Cookie(name, value));
    }

    public void refresh() {
        driver.navigate().refresh();
    }
}
