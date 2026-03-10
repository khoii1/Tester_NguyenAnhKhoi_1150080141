package dtm;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");
    private static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ------------------------------------------------------------------ //
    //  Hàm hỗ trợ
    // ------------------------------------------------------------------ //

    private void enterUsername(String username) {
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    private void enterPassword(String password) {
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    private void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    private String getErrorMessage() {
        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return errorElement.getText();
    }

    // ------------------------------------------------------------------ //
    //  Test cases
    // ------------------------------------------------------------------ //

    // Test case 1: Đăng nhập thành công với tài khoản hợp lệ
    @Test(description = "Dang nhap thanh cong voi tai khoan hop le, chuyen huong den /inventory.html")
    public void testLoginSuccess() {
        enterUsername("standard_user");
        enterPassword("secret_sauce");
        clickLogin();

        wait.until(ExpectedConditions.urlContains("/inventory.html"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/inventory.html"),
                "Dang nhap thanh cong nhung URL khong chua '/inventory.html'. URL hien tai: " + currentUrl);
    }

    // Test case 2: Đăng nhập với mật khẩu sai
    @Test(description = "Dang nhap voi mat khau sai, phai hien thi thong bao loi")
    public void testLoginWrongPassword() {
        enterUsername("standard_user");
        enterPassword("wrong_password");
        clickLogin();

        String errorText = getErrorMessage();
        assertTrue(errorText.toLowerCase().contains("username and password do not match"),
                "Thong bao loi khong dung. Noi dung hien tai: " + errorText);
    }

    // Test case 3: Đăng nhập khi để trống username
    @Test(description = "Dang nhap voi username trong, phai hien thi 'Username is required'")
    public void testLoginEmptyUsername() {
        // Bỏ trống username, chỉ nhập password
        enterPassword("secret_sauce");
        clickLogin();

        String errorText = getErrorMessage();
        assertTrue(errorText.contains("Username is required"),
                "Thong bao loi khong dung. Noi dung hien tai: " + errorText);
    }

    // Test case 4: Đăng nhập khi để trống password
    @Test(description = "Dang nhap voi password trong, phai hien thi 'Password is required'")
    public void testLoginEmptyPassword() {
        enterUsername("standard_user");
        // Bỏ trống password
        clickLogin();

        String errorText = getErrorMessage();
        assertTrue(errorText.contains("Password is required"),
                "Thong bao loi khong dung. Noi dung hien tai: " + errorText);
    }

    // Test case 5: Đăng nhập với tài khoản bị khóa
    @Test(description = "Dang nhap voi tai khoan bi khoa, phai hien thi thong bao 'locked out'")
    public void testLoginLockedUser() {
        enterUsername("locked_out_user");
        enterPassword("secret_sauce");
        clickLogin();

        String errorText = getErrorMessage();
        assertTrue(errorText.contains("Sorry, this user has been locked out"),
                "Thong bao loi khong dung. Noi dung hien tai: " + errorText);
    }
}
