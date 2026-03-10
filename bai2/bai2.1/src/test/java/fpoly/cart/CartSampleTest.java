package fpoly.cart;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Minh họa cách testng.xml chạy toàn bộ package fpoly.cart bằng thẻ <package>.
 * Mọi class test trong package này đều được tự động phát hiện và chạy.
 */
public class CartSampleTest {

    /**
     * Test đơn giản thuộc group "smoke" – minh họa package test được chạy từ testng.xml.
     */
    @Test(description = "Smoke test: xac nhan module Cart duoc load va chay tu package fpoly.cart",
          groups = {"smoke"})
    public void verifyCartModuleLoaded() {
        System.out.println("[CartSampleTest – smoke] Package fpoly.cart da duoc testng.xml quet va chay.");

        String moduleName = "Cart";
        assertTrue(moduleName != null && !moduleName.isEmpty(),
                "Ten module Cart khong duoc null hoac rong – module chua duoc khoi tao!");
    }
}
