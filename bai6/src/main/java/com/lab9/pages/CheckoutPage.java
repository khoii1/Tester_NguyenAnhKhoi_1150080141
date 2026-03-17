package com.lab9.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.lab9.core.BasePage;

/**
 * Minimal checkout page object to support Cart navigation flow.
 */
public class CheckoutPage extends BasePage {

    @FindBy(css = "span.title")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOf(pageTitle));
            return "Checkout: Your Information".equals(title.getText().trim());
        } catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        }
    }

    public CheckoutPage fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        postalCodeField.clear();
        postalCodeField.sendKeys(postalCode);
        return this;
    }

    public CheckoutOverviewPage continueCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return new CheckoutOverviewPage(driver);
    }
}
