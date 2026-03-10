package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CartTest – chay tren Thread rieng KHAC voi LoginTest (parallel="classes").
 * Nho ThreadLocal, driver cua CartTest hoan toan doc lap voi driver cua LoginTest.
 */
public class CartTest extends BaseTest {

    @Test(description = "Kiem tra dieu huong den trang inventory (gio hang)")
    public void testCartPageNavigation() {
        System.out.println("[CartTest] testCartPageNavigation – thread: "
                + Thread.currentThread().getName()
                + " | driver instance: " + driver().hashCode());

        driver().get("https://www.saucedemo.com");

        String currentUrl = driver().getCurrentUrl();
        System.out.println("[CartTest] URL hien tai: " + currentUrl);

        Assert.assertNotNull(currentUrl,
                "URL khong duoc null!");
        Assert.assertTrue(currentUrl.contains("saucedemo"),
                "URL phai chua 'saucedemo' – trang khong dung!");
    }

    @Test(description = "Kiem tra title trang SauceDemo tu CartTest")
    public void testCartPageTitle() {
        System.out.println("[CartTest] testCartPageTitle – thread: "
                + Thread.currentThread().getName()
                + " | driver instance: " + driver().hashCode());

        driver().get("https://www.saucedemo.com");

        String title = driver().getTitle();
        System.out.println("[CartTest] Title hien tai: " + title);

        Assert.assertFalse(title == null || title.isEmpty(),
                "Title trang khong duoc null hoac rong!");
    }
}
