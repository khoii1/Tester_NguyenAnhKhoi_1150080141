package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Demo tests to validate BaseTest setup/teardown and screenshot-on-failure behavior.
 */
@Feature("Kiểm tra hạ tầng kiểm thử")
public class BaseTestDemoTest extends BaseTest {

    @Test
    @Story("Xác nhận trạng thái trang tải ban đầu")
    @Description("Kiểm tra driver load đúng title của Swag Labs")
    @Severity(SeverityLevel.MINOR)
    public void passTestShouldOpenConfiguredUrl() {
        Allure.step("Kiểm tra tiêu đề trang", () -> {
            String title = getDriver().getTitle();
            Assert.assertTrue(title.contains("Swag Labs"), "Title should contain 'Swag Labs'. Actual: " + title);
        });
    }

    @Test
    @Story("Xác nhận URL đúng domain")
    @Description("Kiểm tra hệ thống trỏ về saucedemo.com")
    @Severity(SeverityLevel.MINOR)
    public void passTestLoginPageLoaded() {
        Allure.step("Kiểm tra URL hiện tại", () -> {
            String currentUrl = getDriver().getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("saucedemo.com"), "URL should contain 'saucedemo.com'. Actual: " + currentUrl);
        });
    }
}
