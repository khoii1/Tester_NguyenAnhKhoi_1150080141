package dtm.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * InventoryPage – Page Object cho trang danh sách sản phẩm (sau khi đăng nhập).
 * URL: https://www.saucedemo.com/inventory.html
 */
public class InventoryPage {

    private final WebDriver driver;

    // --- Locators ---
    private final By productTitles    = By.cssSelector(".inventory_item_name");
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon         = By.cssSelector(".shopping_cart_link");
    private final By sortDropdown     = By.cssSelector(".product_sort_container");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    /** Lấy danh sách tên sản phẩm hiển thị trên trang */
    public List<String> getProductNames() {
        return driver.findElements(productTitles)
                     .stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

    /** Thêm sản phẩm vào giỏ theo tên */
    public InventoryPage addToCartByName(String productName) {
        // Tìm nút Add to cart tương ứng với sản phẩm
        String buttonId = "add-to-cart-" + productName.toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-+", "-");
        driver.findElement(By.id(buttonId)).click();
        return this;
    }

    /** Lấy số sản phẩm đang có trong giỏ (từ badge icon) */
    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) return 0;
        try {
            return Integer.parseInt(badges.get(0).getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Click vào icon giỏ hàng để sang CartPage */
    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    /** Kiểm tra trang inventory đang hiển thị */
    public boolean isDisplayed() {
        return driver.getCurrentUrl().contains("inventory");
    }
}
