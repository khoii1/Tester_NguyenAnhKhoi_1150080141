package com.lab9.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.lab9.core.BasePage;

/**
 * Page Object for SauceDemo cart page.
 */
public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "button.cart_button")
    private List<WebElement> removeButtons;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> itemNames;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();
        }
        return this;
    }

    public CheckoutPage goToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver);
    }

    public List<String> getItemNames() {
        return itemNames.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }
}
