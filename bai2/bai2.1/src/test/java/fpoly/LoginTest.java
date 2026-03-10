package fpoly;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Minh họa cách nhận tham số từ testng.xml bằng @Parameters.
 * Tham số baseUrl và browser được khai báo trong <parameter> của suite.
 */
public class LoginTest {

    // ---------------------------------------------------------------
    // Test 1: Kiểm tra baseUrl nhận vào hợp lệ (không null, không rỗng)
    // ---------------------------------------------------------------
    @Parameters({"baseUrl", "browser"})
    @Test(description = "Kiem tra tham so baseUrl nhan tu testng.xml khong null va khong rong")
    public void verifyBaseUrlParameter(String baseUrl, String browser) {
        // In ra console để minh họa việc truyền tham số thành công
        System.out.println("[LoginTest] baseUrl = " + baseUrl);
        System.out.println("[LoginTest] browser  = " + browser);

        assertFalse(baseUrl == null || baseUrl.trim().isEmpty(),
                "baseUrl nhan tu testng.xml khong duoc null hoac rong!");
    }

    // ---------------------------------------------------------------
    // Test 2: Thuộc group "smoke" – kiểm tra baseUrl chứa "saucedemo"
    // ---------------------------------------------------------------
    @Parameters({"baseUrl", "browser"})
    @Test(description = "Smoke test: baseUrl phai tro den saucedemo.com",
          groups = {"smoke"})
    public void smokeLoginPage(String baseUrl, String browser) {
        System.out.println("[LoginTest – smoke] Dang kiem tra baseUrl: " + baseUrl);

        assertTrue(baseUrl.contains("saucedemo"),
                "baseUrl '" + baseUrl + "' khong tro den saucedemo.com!");
    }
}
