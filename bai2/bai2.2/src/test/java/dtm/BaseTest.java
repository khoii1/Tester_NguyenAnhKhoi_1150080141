package dtm;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * BaseTest – lop cha chung cho tat ca test class.
 * Chiu trach nhiem khoi dong va dong WebDriver truoc/sau moi test method.
 */
public class BaseTest {

    /**
     * Nhan tham so "browser" tu testng.xml, khoi tao driver tuong ung.
     * @BeforeMethod chay truoc TUNG test method trong class con.
     */
    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        System.out.println("[BaseTest] setUp – browser: " + browser
                + " | thread: " + Thread.currentThread().getName());
        DriverFactory.initDriver(browser);
    }

    /**
     * Dong driver sau moi test method.
     * @AfterMethod dam bao driver luon duoc giai phong du test pass hay fail.
     */
    @AfterMethod
    public void tearDown() {
        System.out.println("[BaseTest] tearDown – dong driver | thread: "
                + Thread.currentThread().getName());
        DriverFactory.quitDriver();
    }

    /**
     * Ham tien ich de class con lay driver cua thread hien tai.
     */
    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }
}
