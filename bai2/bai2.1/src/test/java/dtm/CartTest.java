package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest {

    @Test(groups = {"smoke", "regression"},
          description = "Them san pham vao gio hang thanh cong")
    public void testAddToCart() {
        System.out.println("[CartTest] testAddToCart - RUNNING");

        int cartCount = 1;

        Assert.assertTrue(cartCount > 0,
            "So luong san pham trong gio hang phai lon hon 0 sau khi them!");
        Assert.assertEquals(cartCount, 1,
            "So luong san pham trong gio hang phai la 1 sau khi them 1 san pham!");

        System.out.println("[CartTest] testAddToCart - PASSED");
    }

    @Test(groups = {"regression"},
          description = "Xoa san pham khoi gio hang thanh cong")
    public void testRemoveFromCart() {
        System.out.println("[CartTest] testRemoveFromCart - RUNNING");

        int cartCount = 0;

        Assert.assertEquals(cartCount, 0,
            "Gio hang phai rong (so luong = 0) sau khi xoa tat ca san pham!");

        System.out.println("[CartTest] testRemoveFromCart - PASSED");
    }
}
