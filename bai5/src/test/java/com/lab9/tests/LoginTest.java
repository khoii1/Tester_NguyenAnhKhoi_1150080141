package com.lab9.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.utils.ConfigReader;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Feature("Đăng nhập hệ thống")
public class LoginTest extends BaseTest {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON   = By.id("login-button");
    private static final By ERROR_MESSAGE  = By.cssSelector("[data-test='error']");
    private static final By PRODUCT_TITLE  = By.cssSelector(".title");

    @Test(groups = "smoke")
    @Story("Kiểm tra mở trang đăng nhập")
    @Description("Kiểm thử tiêu đề trang đăng nhập Swag Labs")
    @Severity(SeverityLevel.NORMAL)
    public void smokeLoginPageDisplayed() {
        Allure.step("Mở trang đăng nhập", () -> {
            // Đã mở ở BaseTest setUp() -> Xác nhận url/title
            String title = getDriver().getTitle();
            Allure.step("Xác nhận chuyển trang thành công: " + title, () -> {
                Assert.assertTrue(title.contains("Swag Labs"),
                        "Login page title mismatch. Actual: " + title);
            });
        });
    }

    @Test(groups = "smoke")
    @Story("UC-001: Đăng nhập bằng tài khoản hợp lệ")
    @Description("Kiểm thử đăng nhập thành công với username và password chuẩn")
    @Severity(SeverityLevel.CRITICAL)
    public void smokeLoginSuccess() {
        ConfigReader config = ConfigReader.getInstance();
        
        Allure.step("Nhập username và mật khẩu", () -> {
            getDriver().findElement(USERNAME_INPUT).sendKeys(config.getStandardUsername());
            getDriver().findElement(PASSWORD_INPUT).sendKeys(config.getStandardPassword());
        });
        
        Allure.step("Click nút Đăng nhập", () -> {
            getDriver().findElement(LOGIN_BUTTON).click();
        });

        Allure.step("Kiểm tra kết quả: chuyển sang trang Products", () -> {
            String pageTitle = getDriver().findElement(PRODUCT_TITLE).getText();
            Assert.assertEquals(pageTitle, "Products",
                    "Should redirect to Products page after successful login.");
        });
    }

    @Test(groups = "smoke")
    @Story("UC-002: Đăng nhập bằng tài khoản bị khóa")
    @Description("Kiểm thử hiện thông báo lỗi khi dùng tài khoản locked_out_user")
    @Severity(SeverityLevel.NORMAL)
    public void smokeLoginFail() {
        ConfigReader config = ConfigReader.getInstance();
        
        Allure.step("Nhập username bị khóa và mật khẩu sai", () -> {
            getDriver().findElement(USERNAME_INPUT).sendKeys(config.getLockedOutUsername());
            getDriver().findElement(PASSWORD_INPUT).sendKeys(config.getInvalidPassword());
        });
        
        Allure.step("Click nút Đăng nhập", () -> {
            getDriver().findElement(LOGIN_BUTTON).click();
        });

        Allure.step("Kiểm tra kết quả: thông báo lỗi hiển thị", () -> {
            boolean errorVisible = getDriver().findElement(ERROR_MESSAGE).isDisplayed();
            Assert.assertTrue(errorVisible, "Error message should be displayed on failed login.");
        });
    }
}
