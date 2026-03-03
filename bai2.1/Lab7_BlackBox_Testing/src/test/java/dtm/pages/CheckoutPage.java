package dtm.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * CheckoutPage – Page Object cho luồng thanh toán.
 * Gồm 2 bước:
 *   Step 1 – https://www.saucedemo.com/checkout-step-one.html  (nhập thông tin)
 *   Step 2 – https://www.saucedemo.com/checkout-step-two.html  (xác nhận đơn)
 *   Complete – https://www.saucedemo.com/checkout-complete.html (thành công)
 */
public class CheckoutPage {

    private final WebDriver driver;

    // --- Locators – Step 1 ---
    private final By firstNameInput  = By.id("first-name");
    private final By lastNameInput   = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton  = By.id("continue");
    // Saucedemo dùng data-test="error" cho cả login lẫn checkout error
    private final By errorMessage    = By.cssSelector("[data-test='error']");

    // --- Locators – Step 2 ---
    private final By finishButton    = By.cssSelector("[data-test='finish']");
    private final By orderSummary    = By.cssSelector(".summary_total_label");

    // --- Locators – Complete ---
    private final By completeHeader  = By.cssSelector(".complete-header");
    private final By backHomeButton  = By.id("back-to-products");

    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---- Step 1 ----

    /** Nhập thông tin giao hàng */
    public CheckoutPage enterShippingInfo(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        driver.findElement(postalCodeInput).clear();
        driver.findElement(postalCodeInput).sendKeys(postalCode);
        return this;
    }

    /** Click Continue sang Step 2 – đợi URL chuyển sang step-two hoặc lỗi hiện */
    public void clickContinue() {
        String urlBefore = driver.getCurrentUrl();
        driver.findElement(continueButton).click();
        // Đợi tối đa 3s để xem URL có thay đổi không (nếu navigate thành công)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> !d.getCurrentUrl().equals(urlBefore));
        } catch (Exception ignored) {
            // URL không đổi → có lỗi validation, tiếp tục để test kiểm tra lỗi
        }
    }

    /** Đợi trang chuyển sang checkout-step-two (dùng trước clickFinish) */
    public void waitForStepTwo() {
        wait.until(d -> d.getCurrentUrl().contains("step-two"));
    }

    /** Lấy thông báo lỗi ở Step 1 (nếu có) – thử nhiều selector */
    public String getErrorMessage() {
        // Thử data-test="error" trước (h3 của SauceDemo)
        try {
            return wait.until(
                ExpectedConditions.presenceOfElementLocated(errorMessage)
            ).getText().trim();
        } catch (Exception ignored) { /* fallback */ }
        // Fallback: .error-message-container h3
        try {
            return driver.findElement(By.cssSelector(".error-message-container h3")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ---- Step 2 ----

    /** Lấy tổng tiền hiển thị ở trang xác nhận */
    public String getOrderTotal() {
        try {
            return driver.findElement(orderSummary).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Click Finish – đảm bảo đang ở step-two rồi mới click */
    public void clickFinish() {
        // Đợi URL xác nhận đang ở step-two
        wait.until(d -> d.getCurrentUrl().contains("step-two"));
        // Tìm nút bằng id (ổn định nhất trên SauceDemo)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();
    }

    // ---- Complete ----

    /** Lấy tiêu đề trang hoàn thành đơn hàng */
    public String getCompleteHeader() {
        try {
            return driver.findElement(completeHeader).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Click Back Home quay về trang sản phẩm */
    public void clickBackHome() {
        driver.findElement(backHomeButton).click();
    }

    /** Kiểm tra đang ở trang checkout step 1 */
    public boolean isOnStepOne() {
        return driver.getCurrentUrl().contains("step-one");
    }

    /** Kiểm tra đang ở trang thanh toán thành công */
    public boolean isOrderComplete() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }
}
