package dtm.data;

import org.testng.annotations.DataProvider;

/**
 * DangNhapData – DataProvider cung cấp dữ liệu cho các test đăng nhập.
 *
 * Trang mục tiêu: https://www.saucedemo.com/
 * Tài khoản hợp lệ: standard_user / secret_sauce
 * Tài khoản bị khoá: locked_out_user / secret_sauce
 */
public class DangNhapData {

    /**
     * Dữ liệu đăng nhập HỢP LỆ – mong đợi vào được trang sản phẩm.
     * Cột: username, password
     */
    @DataProvider(name = "dangNhapHopLeData")
    public static Object[][] dangNhapHopLeData() {
        return new Object[][] {
            { "standard_user",       "secret_sauce" },
            { "problem_user",        "secret_sauce" },
            { "performance_glitch_user", "secret_sauce" },
        };
    }

    /**
     * Dữ liệu đăng nhập KHÔNG HỢP LỆ – mong đợi hiện thông báo lỗi.
     * Cột: username, password, thông báo lỗi mong đợi (chứa chuỗi này)
     */
    @DataProvider(name = "dangNhapKhongHopLeData")
    public static Object[][] dangNhapKhongHopLeData() {
        return new Object[][] {
            { "",               "",               "Username is required"                           },
            { "standard_user",  "",               "Password is required"                           },
            { "locked_out_user","secret_sauce",   "Sorry, this user has been locked out"           },
            { "wrong_user",     "wrong_pass",     "Username and password do not match"             },
        };
    }
}
