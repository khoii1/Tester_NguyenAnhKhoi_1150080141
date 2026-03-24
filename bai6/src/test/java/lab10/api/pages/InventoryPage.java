package lab10.api.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By inventoryContainer = By.id("inventory_container");
    private final By addToCartButtons = By.cssSelector("button.btn_inventory");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
        return driver.getCurrentUrl().contains("inventory");
    }

    public void addFirstTwoItemsToCart() {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        buttons.get(0).click();
        buttons.get(1).click();
    }

    public int getCartBadgeCount() {
        String badgeText = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        return Integer.parseInt(badgeText);
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }
}
