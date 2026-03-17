package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.pages.InventoryPage;
import com.lab9.pages.LoginPage;
import com.lab9.utils.ConfigReader;

/**
 * Login scenarios for SauceDemo.
 */
public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        ConfigReader configReader = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

        InventoryPage inventoryPage = loginPage.login(
            configReader.getStandardUsername(),
            configReader.getStandardPassword()
        );

        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should load after successful login.");
    }

    @Test
    public void testLoginWithInvalidPasswordShowsError() {
        ConfigReader configReader = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure(configReader.getStandardUsername(), configReader.getInvalidPassword());

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown for invalid password.");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Username and password do not match"),
            "Expected invalid credential message."
        );
    }

    @Test
    public void testLoginWithLockedOutUserShowsError() {
        ConfigReader configReader = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure(configReader.getLockedOutUsername(), configReader.getStandardPassword());

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown for locked user.");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Sorry, this user has been locked out."),
            "Expected locked out message."
        );
    }
}
