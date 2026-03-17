package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.pages.CartPage;
import com.lab9.pages.InventoryPage;
import com.lab9.pages.LoginPage;

/**
 * Cart scenarios for SauceDemo.
 */
public class CartTest extends BaseTest {

    @Test
    public void testAddFirstItemToCartUpdatesBadge() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        inventoryPage.addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Cart badge should show 1 after adding first item.");
    }

    @Test
    public void testAddSpecificItemThenOpenCart() {
        LoginPage loginPage = new LoginPage(getDriver());
        CartPage cartPage = loginPage
            .login("standard_user", "secret_sauce")
            .addItemByName("Sauce Labs Backpack")
            .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart should contain exactly one item.");
        Assert.assertTrue(cartPage.getItemNames().contains("Sauce Labs Backpack"), "Expected item in cart.");
    }

    @Test
    public void testEmptyCartReturnsZeroItems() {
        LoginPage loginPage = new LoginPage(getDriver());
        CartPage cartPage = loginPage
            .login("standard_user", "secret_sauce")
            .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Empty cart should return zero items.");
    }

    @Test
    public void testRemoveFirstItemFromCart() {
        LoginPage loginPage = new LoginPage(getDriver());
        CartPage cartPage = loginPage
            .login("standard_user", "secret_sauce")
            .addFirstItemToCart()
            .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1, "Precondition failed: cart should have one item.");

        cartPage.removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart should be empty after removing first item.");
    }
}
