package dtm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import dtm.base.BaseTest;
import dtm.pages.CartPage;
import dtm.pages.CheckoutPage;
import dtm.pages.InventoryPage;
import dtm.pages.LoginPage;

/**
 * TC_CheckoutTest – Kiểm thử luồng thanh toán (checkout) trên SauceDemo.
 *
 * Luồng: Đăng nhập → Thêm sản phẩm → Giỏ hàng → Checkout Step 1 → Step 2 → Complete
 */
public class TC_CheckoutTest extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void dangNhapVaThemSanPham() {
        // Đăng nhập
        new LoginPage(getDriver()).open().login("standard_user", "secret_sauce");

        // Thêm sản phẩm đầu tiên vào giỏ
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addToCartByName(inventoryPage.getProductNames().get(0));

        // Vào giỏ hàng
        inventoryPage.goToCart();
    }

    /**
     * TC_Checkout_01 (smoke) – Hoàn thành checkout với dữ liệu hợp lệ.
     * Kiểm tra: trang complete hiển thị sau khi finish.
     */
    @Test(groups = {"smoke"},
          description = "TC_Checkout_01 – Checkout thành công")
    public void TC_Checkout_01_checkoutThanhCong() {
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.enterShippingInfo("Nguyen", "Van A", "70000");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete(),
                "TC_Checkout_01 FAIL: Không đến trang checkout-complete.");

        String header = checkoutPage.getCompleteHeader();
        Assert.assertFalse(header.isEmpty(),
                "TC_Checkout_01 FAIL: Tiêu đề trang complete trống.");
    }

    /**
     * TC_Checkout_02 – Checkout thiếu First Name → phải hiện lỗi, ở lại step-one.
     */
    @Test(groups = {"regression"},
          description = "TC_Checkout_02 – Bỏ trống First Name → hiện lỗi")
    public void TC_Checkout_02_theuFirstNameHienLoi() {
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        // Bỏ trống firstName
        checkoutPage.enterShippingInfo("", "Van A", "70000");
        checkoutPage.clickContinue();

        // Phải ở lại step-one (không navigate sang step-two)
        Assert.assertTrue(checkoutPage.isOnStepOne(),
                "TC_Checkout_02 FAIL: Đã navigate sang step-two dù firstName trống.");

        // Thông báo lỗi phải hiện
        String error = checkoutPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(),
                "TC_Checkout_02 FAIL: Không hiện thông báo lỗi khi bỏ trống First Name.");
        System.out.println("[TC_Checkout_02] Lỗi nhận được: " + error);
    }

    /**
     * TC_Checkout_03 – Checkout thiếu Postal Code → phải hiện lỗi, ở lại step-one.
     */
    @Test(groups = {"regression"},
          description = "TC_Checkout_03 – Bỏ trống Postal Code → hiện lỗi")
    public void TC_Checkout_03_theuPostalCodeHienLoi() {
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.enterShippingInfo("Nguyen", "Van A", ""); // bỏ postal code
        checkoutPage.clickContinue();

        // Phải ở lại step-one
        Assert.assertTrue(checkoutPage.isOnStepOne(),
                "TC_Checkout_03 FAIL: Đã navigate sang step-two dù postal code trống.");

        String error = checkoutPage.getErrorMessage();
        Assert.assertFalse(error.isEmpty(),
                "TC_Checkout_03 FAIL: Không hiện thông báo lỗi khi bỏ trống Postal Code.");
        System.out.println("[TC_Checkout_03] Lỗi nhận được: " + error);
    }
}
