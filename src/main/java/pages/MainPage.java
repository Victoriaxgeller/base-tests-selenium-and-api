package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.lang.String.format;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    private String categoryByName(String name) {
        return format("//*[@class='css-1gpccxq']/span[text()='%s']", name);
    }

    @FindBy(css = "sub-cat-1944-root-link")
    private WebElement subCategory;

    @FindBy(css = "[data-testid='cat-1944']")
    private WebElement menuItems;

    @FindBy(xpath = "//*[@class='css-1p85e15']/a")
    private WebElement lookThroughAllAddsText;


    public void clickOnMainSlider() {
        menuItems.click();
    }

    @Step("Click on the link {name}")
    public void clickOnCategory(String name) {
        WebElement category = driver.findElement(By.xpath(categoryByName(name)));
        category.click();
    }

    public String getTextFormLookThroughAllAddsLink() {
        return lookThroughAllAddsText.getText();
    }


}
