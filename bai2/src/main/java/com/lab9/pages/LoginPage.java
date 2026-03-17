package com.lab9.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.lab9.core.BasePage;

/**
 * Page Object for SauceDemo login page.
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public InventoryPage login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).clear();
        usernameField.sendKeys(user);

        passwordField.clear();
        passwordField.sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).clear();
        usernameField.sendKeys(user);

        passwordField.clear();
        passwordField.sendKeys(pass);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public boolean isErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return false;
        }
    }

    public boolean isLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(loginButton)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        }
    }
}
