package dtm.data;

import org.testng.annotations.DataProvider;

/**
 * GioHangData – DataProvider cung cấp dữ liệu cho các test giỏ hàng.
 */
public class GioHangData {

    /**
     * Dữ liệu thêm sản phẩm vào giỏ hàng.
     * Cột: tên sản phẩm (hiển thị trên trang inventory), số lượng mong đợi trong cart
     */
    @DataProvider(name = "gioHangData")
    public static Object[][] gioHangData() {
        return new Object[][] {
            { "Sauce Labs Backpack",          1 },
            { "Sauce Labs Bike Light",        1 },
            { "Sauce Labs Bolt T-Shirt",      1 },
        };
    }

    /**
     * Dữ liệu thêm nhiều sản phẩm cùng lúc rồi kiểm tra badge số lượng.
     * Cột: mảng tên sản phẩm, số badge mong đợi
     */
    @DataProvider(name = "gioHangNhieuSanPhamData")
    public static Object[][] gioHangNhieuSanPhamData() {
        return new Object[][] {
            { new String[]{ "Sauce Labs Backpack", "Sauce Labs Bike Light" }, 2 },
            { new String[]{ "Sauce Labs Backpack", "Sauce Labs Bike Light",
                            "Sauce Labs Bolt T-Shirt" },                     3 },
        };
    }
}
