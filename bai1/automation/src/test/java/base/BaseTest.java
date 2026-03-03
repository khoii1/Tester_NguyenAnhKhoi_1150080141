package base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * BaseTest – lớp cha cho tất cả test class.
 *
 * Chịu trách nhiệm:
 *   – Đọc cấu hình từ config.properties
 *   – Khởi động / đóng WebDriver
 *   – Mở trang web cần test
 *   – Chụp screenshot khi test fail
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Đọc 1 lần cho cả class
    private static final Properties config = loadConfig();

    // -------------------------------------------------------
    // Setup – chạy trước mỗi @Test method
    // -------------------------------------------------------
    @BeforeMethod
    public void setUp() {
        String browser    = getConfig("browser", "chrome");
        boolean headless  = Boolean.parseBoolean(getConfig("headless", "false"));
        int implicitWait  = Integer.parseInt(getConfig("implicit.wait", "5"));
        int explicitWait  = Integer.parseInt(getConfig("explicit.wait", "10"));

        driver = createDriver(browser, headless);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // WebDriverWait dùng cho điều kiện explicit
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        // Mở URL form đăng ký
        String appUrl = getConfig("app.url", "");
        driver.get(appUrl);
    }

    // -------------------------------------------------------
    // Teardown – chạy sau mỗi @Test method
    // -------------------------------------------------------
    @AfterMethod
    public void tearDown(ITestResult result) {
        // Chụp screenshot nếu test FAIL
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // -------------------------------------------------------
    // Tạo WebDriver theo loại trình duyệt
    // -------------------------------------------------------
    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser.toLowerCase()) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("--headless");
                return new FirefoxDriver(opts);
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions opts = new EdgeOptions();
                if (headless) opts.addArguments("--headless");
                return new EdgeDriver(opts);
            }
            default: { // chrome
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                if (headless) {
                    opts.addArguments("--headless=new");
                    opts.addArguments("--window-size=1920,1080");
                }
                opts.addArguments("--disable-search-engine-choice-screen");
                return new ChromeDriver(opts);
            }
        }
    }

    // -------------------------------------------------------
    // Chụp screenshot lưu vào thư mục cấu hình
    // -------------------------------------------------------
    protected void takeScreenshot(String testName) {
        if (!(driver instanceof TakesScreenshot)) return;
        try {
            String dir = getConfig("screenshot.dir", "test-output/screenshots");
            Path screenshotDir = Paths.get(dir);
            Files.createDirectories(screenshotDir);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";

            byte[] srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path dest = screenshotDir.resolve(fileName);
            Files.write(dest, srcFile);

            System.out.println("[SCREENSHOT] Saved: " + dest.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SCREENSHOT] Failed to save: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // Load config.properties từ classpath
    // -------------------------------------------------------
    private static Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream is = BaseTest.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            } else {
                System.err.println("[CONFIG] config.properties not found in classpath!");
            }
        } catch (IOException e) {
            System.err.println("[CONFIG] Error loading config: " + e.getMessage());
        }
        return props;
    }

    /** Lấy giá trị config, trả về defaultValue nếu không tìm thấy */
    protected static String getConfig(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }
}
