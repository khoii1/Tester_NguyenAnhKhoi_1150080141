package com.lab9.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.lab9.core.BasePage;

/**
 * Page Object for SauceDemo inventory page.
 */
public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = "button.btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "a.shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(inventoryList)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        }
    }

    public InventoryPage addFirstItemToCart() {
        if (addToCartButtons.isEmpty()) {
            throw new IllegalStateException("No Add to Cart buttons found on Inventory page.");
        }
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtons.get(0))).click();
        return this;
    }

    public InventoryPage addItemByName(String name) {
        for (WebElement item : inventoryItems) {
            WebElement itemName = item.findElement(By.cssSelector(".inventory_item_name"));
            if (name.equals(itemName.getText().trim())) {
                WebElement addButton = item.findElement(By.cssSelector("button.btn_inventory"));
                wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
                return this;
            }
        }

        throw new NoSuchElementException("Item not found on inventory page: " + name);
    }

    public int getCartItemCount() {
        try {
            String badgeText = wait.until(ExpectedConditions.visibilityOf(cartBadge)).getText();
            return Integer.parseInt(badgeText.trim());
        } catch (org.openqa.selenium.TimeoutException ex) {
            return 0;
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return 0;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver);
    }
}
