package dtm.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * CartPage – Page Object cho trang giỏ hàng.
 * URL: https://www.saucedemo.com/cart.html
 */
public class CartPage {

    private final WebDriver driver;

    // --- Locators ---
    private final By cartItems        = By.cssSelector(".cart_item");
    private final By itemNames        = By.cssSelector(".inventory_item_name");
    private final By checkoutButton   = By.id("checkout");
    private final By continueShopBtn  = By.id("continue-shopping");
    // data-test selector ổn định hơn trên SauceDemo
    private final By removeButtons    = By.cssSelector("[data-test^='remove']");

    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Lấy danh sách tên sản phẩm trong giỏ */
    public List<String> getCartItemNames() {
        return driver.findElements(itemNames)
                     .stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

    /** Lấy số lượng item trong giỏ */
    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    /** Click nút Checkout để sang trang checkout */
    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    /** Click Continue Shopping để quay lại trang sản phẩm */
    public void clickContinueShopping() {
        driver.findElement(continueShopBtn).click();
    }

    /** Xoá sản phẩm đầu tiên trong giỏ và đợi DOM cập nhật */
    public void removeFirstItem() {
        List<WebElement> btns = driver.findElements(removeButtons);
        if (btns.isEmpty()) return;
        WebElement btn = btns.get(0);
        btn.click();
        // Đợi nút vừa bấm biến mất (DOM đã cập nhật)
        try {
            wait.until(ExpectedConditions.stalenessOf(btn));
        } catch (Exception ignored) {
            // nếu timeout vẫn tiếp tục, không ném lỗi
        }
    }

    /** Kiểm tra trang cart đang hiển thị */
    public boolean isDisplayed() {
        return driver.getCurrentUrl().contains("cart");
    }
}
