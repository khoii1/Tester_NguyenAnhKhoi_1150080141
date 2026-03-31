package com.lab9.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.utils.ConfigReader;

/**
 * Smoke tests for the login page of saucedemo.com.
 * Credentials are read from environment variables (APP_USERNAME / APP_PASSWORD)
 * via ConfigReader — no passwords are hardcoded here.
 */
public class LoginTest extends BaseTest {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");
    private static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");
    private static final By PRODUCT_TITLE  = By.cssSelector(".title");

    @Test(groups = "smoke")
    public void smokeLoginPageDisplayed() {
        String title = getDriver().getTitle();
        Assert.assertTrue(title.contains("Swag Labs"),
                "Login page title mismatch. Actual: " + title);
    }

    @Test(groups = "smoke")
    public void smokeLoginSuccess() {
        ConfigReader config = ConfigReader.getInstance();
        // Doc tu env var APP_USERNAME / APP_PASSWORD (khong hardcode)
        getDriver().findElement(USERNAME_INPUT).sendKeys(config.getStandardUsername());
        getDriver().findElement(PASSWORD_INPUT).sendKeys(config.getStandardPassword());
        getDriver().findElement(LOGIN_BUTTON).click();

        String pageTitle = getDriver().findElement(PRODUCT_TITLE).getText();
        Assert.assertEquals(pageTitle, "Products",
                "Should redirect to Products page after successful login.");
    }

    @Test(groups = "smoke")
    public void smokeLoginFail() {
        ConfigReader config = ConfigReader.getInstance();
        getDriver().findElement(USERNAME_INPUT).sendKeys(config.getLockedOutUsername());
        getDriver().findElement(PASSWORD_INPUT).sendKeys(config.getInvalidPassword());
        getDriver().findElement(LOGIN_BUTTON).click();

        boolean errorVisible = getDriver().findElement(ERROR_MESSAGE).isDisplayed();
        Assert.assertTrue(errorVisible, "Error message should be displayed on failed login.");
    }
}
