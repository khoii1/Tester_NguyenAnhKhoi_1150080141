package dtm;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TitleTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // Test case 1: Kiểm thử tiêu đề trang
    @Test(description = "Kiem thu tieu de trang phai la 'Swag Labs'")
    public void testTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "Swag Labs";
        assertEquals(actualTitle, expectedTitle, "Tieu de trang khong dung!");
    }

    // Test case 2: Kiểm thử URL hiện tại
    @Test(description = "Kiem thu URL hien tai phai chua 'saucedemo'")
    public void testURL() {
        String actualUrl = driver.getCurrentUrl();
        assertTrue(actualUrl.contains("saucedemo"), "URL khong hop le!");
    }

    // Test case 3: Kiểm thử page source chứa nội dung đặc trưng của trang đăng nhập
    @Test(description = "Kiem thu page source chua noi dung dac trung cua trang dang nhap")
    public void testPageSource() {
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Swag Labs"),
                "Page source khong chua ten thuong hieu 'Swag Labs'!");
        assertTrue(pageSource.contains("user-name"),
                "Page source khong chua truong nhap username!");
        assertTrue(pageSource.contains("login-button"),
                "Page source khong chua nut dang nhap!");
    }

    // Test case 4: Kiểm thử form đăng nhập hiển thị đầy đủ
    @Test(description = "Kiem thu form dang nhap hien thi day du (username, password, nut login)")
    public void testLoginFormDisplayed() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton  = driver.findElement(By.id("login-button"));

        assertTrue(usernameField.isDisplayed(),
                "O nhap username khong hien thi tren trang!");
        assertTrue(passwordField.isDisplayed(),
                "O nhap password khong hien thi tren trang!");
        assertTrue(loginButton.isDisplayed(),
                "Nut Login khong hien thi tren trang!");
    }
}
