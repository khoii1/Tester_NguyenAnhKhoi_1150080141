package dtm.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * BaseTest – Lớp cha chung cho mọi test class trong dự án.
 *
 * Sử dụng ThreadLocal<WebDriver> để đảm bảo an toàn khi chạy song song.
 * Tự động chụp screenshot khi test FAIL và lưu vào thư mục /screenshots/.
 */
public class BaseTest {

    // ThreadLocal đảm bảo mỗi thread có driver riêng (an toàn khi song song)
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    // Thư mục lưu screenshot khi fail
    private static final String SCREENSHOT_DIR = "screenshots";

    // ----------------------------------------------------------------
    // SETUP – chạy trước mỗi @Test method
    // ----------------------------------------------------------------
    @BeforeMethod
    public void setup(Method method) {
        System.out.println("\n========================================");
        System.out.println("[START] Test: " + method.getName());
        System.out.println("========================================");

        // Tự động tải ChromeDriver phù hợp với phiên bản Chrome đang cài
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // Tắt thông báo "Chrome is being controlled by automated software"
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Lưu driver vào ThreadLocal của thread hiện tại
        driverThread.set(driver);
    }

    // ----------------------------------------------------------------
    // TEARDOWN – chạy sau mỗi @Test method
    // ----------------------------------------------------------------
    @AfterMethod
    public void teardown(ITestResult result) {
        WebDriver driver = getDriver();

        // Chụp screenshot nếu test FAIL
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("[FAIL] Test: " + result.getName() + " – đang chụp screenshot...");
            takeScreenshot(result.getName());
        }

        // Đóng browser và giải phóng ThreadLocal
        if (driver != null) {
            driver.quit();
        }
        driverThread.remove();

        System.out.println("[END] Test: " + result.getName()
                + " – " + statusText(result.getStatus()));
    }

    // ----------------------------------------------------------------
    // getDriver() – trả về driver của thread hiện tại
    // ----------------------------------------------------------------
    /**
     * Trả về WebDriver của thread đang chạy.
     * Luôn dùng phương thức này thay vì truy cập trực tiếp driverThread.
     */
    public WebDriver getDriver() {
        return driverThread.get();
    }

    // ----------------------------------------------------------------
    // HELPER – chụp screenshot
    // ----------------------------------------------------------------
    private void takeScreenshot(String testName) {
        WebDriver driver = getDriver();
        if (!(driver instanceof TakesScreenshot)) return;

        try {
            // Tạo thư mục screenshots nếu chưa có
            Path dir = Paths.get(SCREENSHOT_DIR);
            Files.createDirectories(dir);

            // Tên file: testName_yyyyMMdd_HHmmss.png
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            Path dest = dir.resolve(fileName);

            byte[] imageBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(dest, imageBytes);

            System.out.println("[SCREENSHOT] Đã lưu: " + dest.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("[SCREENSHOT] Lỗi khi lưu screenshot: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // HELPER – chuyển status code sang text
    // ----------------------------------------------------------------
    private String statusText(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "PASSED";
            case ITestResult.FAILURE: return "FAILED";
            case ITestResult.SKIP:    return "SKIPPED";
            default:                  return "UNKNOWN";
        }
    }
}
