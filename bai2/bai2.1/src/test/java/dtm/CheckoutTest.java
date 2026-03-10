package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest {

    @Test(groups = {"smoke", "regression"},
          description = "Xem trang tong quan don hang truoc khi thanh toan")
    public void testCheckoutOverview() {
        System.out.println("[CheckoutTest] testCheckoutOverview - RUNNING");

        String pageTitle = "Checkout: Overview";

        Assert.assertNotNull(pageTitle,
            "Tieu de trang tong quan khong duoc null!");
        Assert.assertTrue(pageTitle.contains("Overview"),
            "Tieu de trang phai chua tu 'Overview' - trang hien tai khong dung!");

        System.out.println("[CheckoutTest] testCheckoutOverview - PASSED");
    }

    @Test(groups = {"regression"},
          description = "Hoan tat qua trinh dat hang thanh cong")
    public void testCheckoutComplete() {
        System.out.println("[CheckoutTest] testCheckoutComplete - RUNNING");

        boolean orderPlaced = true;
        String confirmMessage = "THANK YOU FOR YOUR ORDER";

        Assert.assertTrue(orderPlaced,
            "Trang thai dat hang phai la true sau khi hoan tat thanh toan!");
        Assert.assertNotNull(confirmMessage,
            "Thong bao xac nhan don hang khong duoc null!");

        System.out.println("[CheckoutTest] testCheckoutComplete - PASSED");
    }
}
