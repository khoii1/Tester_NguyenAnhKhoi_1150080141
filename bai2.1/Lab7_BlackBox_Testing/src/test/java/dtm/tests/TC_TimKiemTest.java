package dtm.tests;

import dtm.base.BaseTest;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * TC_TimKiemTest – Kiểm thử chức năng sắp xếp / lọc sản phẩm trên SauceDemo.
 *
 * SauceDemo không có ô tìm kiếm trực tiếp, nên bài này kiểm tra
 * chức năng sort (sắp xếp) sản phẩm theo các tiêu chí khác nhau.
 */
public class TC_TimKiemTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void dangNhap() {
        new LoginPage(getDriver()).open().login("standard_user", "secret_sauce");
    }

    /**
     * TC_TimKiem_01 (smoke) – Trang inventory hiển thị ít nhất 1 sản phẩm.
     */
    @Test(groups = {"smoke"},
          description = "TC_TimKiem_01 – Trang sản phẩm có ít nhất 1 sản phẩm")
    public void TC_TimKiem_01_trangSanPhamCoSanPham() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        List<String> products = inventoryPage.getProductNames();

        Assert.assertFalse(products.isEmpty(),
                "TC_TimKiem_01 FAIL: Danh sách sản phẩm trống.");
    }

    /**
     * TC_TimKiem_02 – Sắp xếp sản phẩm A→Z.
     * Kiểm tra: tên sản phẩm đầu tiên < tên cuối cùng (theo alphabet).
     */
    @Test(groups = {"regression"},
          description = "TC_TimKiem_02 – Sắp xếp A→Z")
    public void TC_TimKiem_02_sapXepAZ() {
        WebDriver driver = getDriver();
        Select sortSelect = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortSelect.selectByValue("az");

        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> names = inventoryPage.getProductNames();

        Assert.assertTrue(names.size() >= 2, "TC_TimKiem_02 FAIL: Cần ít nhất 2 sản phẩm để kiểm tra sort.");

        String first = names.get(0);
        String last  = names.get(names.size() - 1);
        Assert.assertTrue(first.compareToIgnoreCase(last) <= 0,
                "TC_TimKiem_02 FAIL: Sản phẩm chưa được sắp xếp A→Z. First=[" + first + "] Last=[" + last + "]");
    }

    /**
     * TC_TimKiem_03 – Sắp xếp sản phẩm Z→A.
     */
    @Test(groups = {"regression"},
          description = "TC_TimKiem_03 – Sắp xếp Z→A")
    public void TC_TimKiem_03_sapXepZA() {
        WebDriver driver = getDriver();
        Select sortSelect = new Select(driver.findElement(By.cssSelector(".product_sort_container")));
        sortSelect.selectByValue("za");

        InventoryPage inventoryPage = new InventoryPage(driver);
        List<String> names = inventoryPage.getProductNames();

        Assert.assertTrue(names.size() >= 2, "TC_TimKiem_03 FAIL: Cần ít nhất 2 sản phẩm.");

        String first = names.get(0);
        String last  = names.get(names.size() - 1);
        Assert.assertTrue(first.compareToIgnoreCase(last) >= 0,
                "TC_TimKiem_03 FAIL: Sản phẩm chưa được sắp xếp Z→A. First=[" + first + "] Last=[" + last + "]");
    }
}
