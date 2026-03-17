package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.pages.InventoryPage;
import com.lab9.pages.LoginPage;

/**
 * Login scenarios for SauceDemo.
 */
public class LoginTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should load after successful login.");
    }

    @Test
    public void testLoginWithInvalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown for invalid password.");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Username and password do not match"),
            "Expected invalid credential message."
        );
    }

    @Test
    public void testLoginWithLockedOutUserShowsError() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be shown for locked user.");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Sorry, this user has been locked out."),
            "Expected locked out message."
        );
    }
}
