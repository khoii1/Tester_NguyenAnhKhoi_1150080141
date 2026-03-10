package dtm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * DriverFactory – quan ly WebDriver theo tung thread.
 *
 * Vi sao dung ThreadLocal?
 *   Khi TestNG chay song song (parallel="classes"), moi class test chay
 *   tren mot luong (thread) khac nhau. Neu dung bien WebDriver static thong
 *   thuong, cac thread se dung chung 1 driver => xung dot, loi kho debug.
 *   ThreadLocal<WebDriver> dam bao moi thread co ban sao driver RIENG BIET,
 *   hoan toan doc lap voi nhau => an toan khi chay song song.
 */
public class DriverFactory {

    // Moi thread co WebDriver rieng – khong chia se giua cac thread
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Khoi tao WebDriver tuong ung voi browser duoc truyen vao.
     * Duoc goi o @BeforeMethod cua BaseTest.
     */
    public static void initDriver(String browser) {
        WebDriver driver;

        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            // Mac dinh: Chrome
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();

        // Luu driver vao o nho rieng cua thread hien tai
        tlDriver.set(driver);
    }

    /**
     * Lay WebDriver cua thread hien tai.
     * Duoc goi trong cac test method qua BaseTest.driver().
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    /**
     * Dong WebDriver va xoa khoi ThreadLocal.
     * Duoc goi o @AfterMethod cua BaseTest.
     *
     * Vi sao phai goi tlDriver.remove()?
     *   Trong moi truong thread pool (vi du Maven Surefire tai su dung thread),
     *   neu khong goi remove(), tham chieu WebDriver cu van con trong ThreadLocal
     *   => memory leak va co the gay ra loi session cu cho lan chay tiep theo.
     */
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();   // Dong cua so trinh duyet
            tlDriver.remove();       // Xoa tham chieu – tranh memory leak
        }
    }
}
