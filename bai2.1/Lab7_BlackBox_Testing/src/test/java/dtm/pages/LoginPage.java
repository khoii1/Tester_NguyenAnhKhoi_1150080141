package dtm.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * LoginPage – Page Object cho trang đăng nhập https://www.saucedemo.com/
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators ---
    private final By usernameInput  = By.id("user-name");
    private final By passwordInput  = By.id("password");
    private final By loginButton    = By.id("login-button");
    private final By errorMessage   = By.cssSelector(".error-message-container h3");

    public static final String URL = "https://www.saucedemo.com/";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /** Mở trang đăng nhập */
    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    /** Nhập tên đăng nhập */
    public LoginPage enterUsername(String username) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        return this;
    }

    /** Nhập mật khẩu */
    public LoginPage enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        return this;
    }

    /** Click nút Login */
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    /** Đăng nhập nhanh với username + password */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    /** Lấy text thông báo lỗi (nếu có) */
    public String getErrorMessage() {
        try {
            WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return el.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Kiểm tra trang login đang hiển thị */
    public boolean isDisplayed() {
        return driver.findElements(loginButton).size() > 0;
    }
}
