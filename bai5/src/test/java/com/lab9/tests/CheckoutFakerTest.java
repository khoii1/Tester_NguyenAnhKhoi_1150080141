package com.lab9.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.pages.CheckoutOverviewPage;
import com.lab9.pages.CheckoutPage;
import com.lab9.pages.LoginPage;
import com.lab9.utils.ConfigReader;
import com.lab9.utils.TestDataFactory;
import com.lab9.utils.TestDataFactory.CheckoutData;

/**
 * Checkout test that uses Java Faker-generated data.
 */
public class CheckoutFakerTest extends BaseTest {

    @Test(invocationCount = 2)
    public void testCheckoutWithRandomDataTwice() {
        CheckoutData data = TestDataFactory.randomCheckoutData();
        ConfigReader configReader = ConfigReader.getInstance();
        Reporter.log("Generated checkout data: " + data, true);

        CheckoutPage checkoutPage = new LoginPage(getDriver())
            .login(configReader.getStandardUsername(), configReader.getStandardPassword())
            .addFirstItemToCart()
            .goToCart()
            .goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(), "Checkout information page should be loaded.");

        CheckoutOverviewPage overviewPage = checkoutPage
            .fillCheckoutInformation(data.getFirstName(), data.getLastName(), data.getPostalCode())
            .continueCheckout();

        Assert.assertTrue(overviewPage.isLoaded(), "Checkout overview page should be loaded after continue.");
    }
}
