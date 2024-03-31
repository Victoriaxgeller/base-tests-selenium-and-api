package mock.ui;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

@Getter

public class ZapRegistrationPage extends BasePage {


    public ZapRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "zapreg_firstname")
    private WebElement firstNameField;
    @FindBy(id = "zapreg_lastname")
    private WebElement userNsmrField;
    @FindBy(id = "zapreg_email")
    private WebElement emailField;
    @FindBy(id = "zapreg_pass")
    private WebElement passField;
    @FindBy(id = "zapreg_pass_conf")
    private WebElement cpnfirmPassField;

    @FindBy(css = "[name=\"submit_zap\"]")
    private WebElement submitBtn;


    public ZapRegistrationPage fillForm(String name, String lastName, String email) {
        firstNameField.clear();
        firstNameField.sendKeys(name);
        userNsmrField.clear();
        userNsmrField.sendKeys(lastName);

        emailField.clear();
        emailField.sendKeys(email);
        passField.clear();
        passField.sendKeys(email);
        cpnfirmPassField.clear();
        cpnfirmPassField.sendKeys(email);

        submitBtn.click();
        return this;
    }

}

