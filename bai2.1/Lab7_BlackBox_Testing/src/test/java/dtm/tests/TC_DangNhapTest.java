package dtm.tests;

import dtm.base.BaseTest;
import dtm.data.DangNhapData;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC_DangNhapTest – Kiểm thử chức năng đăng nhập trang SauceDemo.
 *
 * Bao gồm:
 *   - Smoke test: đăng nhập hợp lệ
 *   - Test âm: đăng nhập không hợp lệ (DataProvider)
 *   - Test tài khoản bị khoá
 */
public class TC_DangNhapTest extends BaseTest {

    /**
     * TC_DangNhap_01 (smoke) – Đăng nhập thành công với tài khoản hợp lệ.
     * Kiểm tra: sau khi đăng nhập → vào được trang inventory.
     */
    @Test(groups = {"smoke"},
          description = "TC_DangNhap_01 – Đăng nhập thành công")
    public void TC_DangNhap_01_dangNhapThanhCong() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isDisplayed(),
                "TC_DangNhap_01 FAIL: Không vào được trang sản phẩm sau khi đăng nhập.");
    }

    /**
     * TC_DangNhap_02 – Đăng nhập với nhiều tài khoản hợp lệ (DataProvider).
     */
    @Test(groups = {"regression"},
          dataProvider = "dangNhapHopLeData",
          dataProviderClass = DangNhapData.class,
          description = "TC_DangNhap_02 – Đăng nhập hợp lệ với nhiều tài khoản")
    public void TC_DangNhap_02_dangNhapHopLe(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isDisplayed(),
                "TC_DangNhap_02 FAIL: Tài khoản [" + username + "] không vào được trang sản phẩm.");
    }

    /**
     * TC_DangNhap_03 – Đăng nhập với dữ liệu không hợp lệ → phải hiện lỗi.
     */
    @Test(groups = {"regression"},
          dataProvider = "dangNhapKhongHopLeData",
          dataProviderClass = DangNhapData.class,
          description = "TC_DangNhap_03 – Đăng nhập không hợp lệ hiển thị lỗi")
    public void TC_DangNhap_03_dangNhapKhongHopLe(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        loginPage.login(username, password);

        String actualError = loginPage.getErrorMessage();
        Assert.assertFalse(actualError.isEmpty(),
                "TC_DangNhap_03 FAIL: Không hiện thông báo lỗi cho user=[" + username + "]");
        Assert.assertTrue(actualError.contains(expectedError),
                "TC_DangNhap_03 FAIL: Lỗi expected chứa '" + expectedError + "' nhưng actual: '" + actualError + "'");
    }
}
