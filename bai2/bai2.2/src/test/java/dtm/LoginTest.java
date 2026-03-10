package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTest – chay tren Thread rieng (parallel="classes").
 * Driver duoc quan ly boi DriverFactory qua ThreadLocal.
 */
public class LoginTest extends BaseTest {

    @Test(description = "Kiem tra title trang dang nhap SauceDemo")
    public void testLoginPageTitle() {
        System.out.println("[LoginTest] testLoginPageTitle – thread: "
                + Thread.currentThread().getName()
                + " | driver instance: " + driver().hashCode());

        driver().get("https://www.saucedemo.com");

        String title = driver().getTitle();
        System.out.println("[LoginTest] Title hien tai: " + title);

        Assert.assertEquals(title, "Swag Labs",
                "Title trang dang nhap phai la 'Swag Labs'!");
    }

    @Test(description = "Kiem tra URL trang dang nhap chua saucedemo")
    public void testLoginPageUrl() {
        System.out.println("[LoginTest] testLoginPageUrl – thread: "
                + Thread.currentThread().getName()
                + " | driver instance: " + driver().hashCode());

        driver().get("https://www.saucedemo.com");

        String currentUrl = driver().getCurrentUrl();
        System.out.println("[LoginTest] URL hien tai: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("saucedemo"),
                "URL trang dang nhap phai chua 'saucedemo'!");
    }
}
