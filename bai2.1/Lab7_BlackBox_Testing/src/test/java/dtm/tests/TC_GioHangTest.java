package dtm.tests;

import dtm.base.BaseTest;
import dtm.data.GioHangData;
import dtm.pages.CartPage;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * TC_GioHangTest – Kiểm thử chức năng giỏ hàng trên SauceDemo.
 *
 * Bao gồm:
 *   - Thêm 1 sản phẩm vào giỏ
 *   - Kiểm tra badge số lượng
 *   - Mở trang giỏ hàng và xác nhận sản phẩm
 *   - Xoá sản phẩm khỏi giỏ
 */
public class TC_GioHangTest extends BaseTest {

    /** Đăng nhập trước mỗi test trong class này */
    @BeforeMethod(alwaysRun = true)
    public void dangNhap() {
        new LoginPage(getDriver()).open().login("standard_user", "secret_sauce");
    }

    /**
     * TC_GioHang_01 (smoke) – Thêm sản phẩm vào giỏ, kiểm tra badge = 1.
     */
    @Test(groups = {"smoke"},
          description = "TC_GioHang_01 – Thêm 1 sản phẩm, badge = 1")
    public void TC_GioHang_01_themSanPhamVaKiemTraBadge() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        // Thêm sản phẩm đầu tiên trong danh sách
        String productName = inventoryPage.getProductNames().get(0);
        inventoryPage.addToCartByName(productName);

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1,
                "TC_GioHang_01 FAIL: Badge giỏ hàng phải là 1 sau khi thêm 1 sản phẩm.");
    }

    /**
     * TC_GioHang_02 (DataProvider) – Thêm từng sản phẩm, badge phải = 1.
     */
    @Test(groups = {"regression"},
          dataProvider = "gioHangData",
          dataProviderClass = GioHangData.class,
          description = "TC_GioHang_02 – Thêm sản phẩm theo DataProvider")
    public void TC_GioHang_02_themSanPhamTheoData(String productName, int expectedBadge) {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addToCartByName(productName);

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), expectedBadge,
                "TC_GioHang_02 FAIL: Badge không đúng cho sản phẩm [" + productName + "]");
    }

    /**
     * TC_GioHang_03 – Mở trang giỏ hàng, xác nhận sản phẩm có trong giỏ.
     */
    @Test(groups = {"regression"},
          description = "TC_GioHang_03 – Mở trang cart, xác nhận sản phẩm có trong giỏ")
    public void TC_GioHang_03_xacNhanSanPhamTrongGio() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        String productName = inventoryPage.getProductNames().get(0);
        inventoryPage.addToCartByName(productName);
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.isDisplayed(),
                "TC_GioHang_03 FAIL: Không vào được trang Cart.");
        Assert.assertTrue(cartPage.getCartItemNames().contains(productName),
                "TC_GioHang_03 FAIL: Sản phẩm [" + productName + "] không có trong giỏ.");
    }

    /**
     * TC_GioHang_04 – Xoá sản phẩm khỏi giỏ, số lượng về 0.
     */
    @Test(groups = {"regression"},
          description = "TC_GioHang_04 – Xoá sản phẩm khỏi giỏ hàng")
    public void TC_GioHang_04_xoaSanPhamKhoiGio() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addToCartByName(inventoryPage.getProductNames().get(0));
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(getDriver());
        int countBefore = cartPage.getCartItemCount();
        cartPage.removeFirstItem();
        int countAfter = cartPage.getCartItemCount();

        Assert.assertEquals(countAfter, countBefore - 1,
                "TC_GioHang_04 FAIL: Số lượng sản phẩm không giảm sau khi xoá.");
    }
}
