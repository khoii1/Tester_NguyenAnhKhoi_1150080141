package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    @Test(groups = {"smoke", "regression"},
          description = "Dang nhap thanh cong voi tai khoan hop le")
    public void testLoginSuccess() {
        System.out.println("[LoginTest] testLoginSuccess - RUNNING");

        String username = "standard_user";
        String password = "secret_sauce";

        Assert.assertNotNull(username, "Username khong duoc null!");
        Assert.assertFalse(username.isEmpty(), "Username khong duoc rong!");
        Assert.assertEquals(password, "secret_sauce", "Mat khau phai dung la 'secret_sauce'!");

        System.out.println("[LoginTest] testLoginSuccess - PASSED");
    }

    @Test(groups = {"regression"},
          description = "Dang nhap sai mat khau phai tra ve thong bao loi")
    public void testLoginWrongPassword() {
        System.out.println("[LoginTest] testLoginWrongPassword - RUNNING");

        String wrongPassword = "wrong_password";
        boolean isLoginFailed = !wrongPassword.equals("secret_sauce");

        Assert.assertTrue(isLoginFailed,
            "Dang nhap voi mat khau sai phai that bai - isLoginFailed phai la true!");

        System.out.println("[LoginTest] testLoginWrongPassword - PASSED");
    }
}
