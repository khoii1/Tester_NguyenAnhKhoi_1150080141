package com.lab9.tests;

import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.dataproviders.UserDataProvider;
import com.lab9.models.UserData;
import com.lab9.pages.InventoryPage;
import com.lab9.pages.LoginPage;

/**
 * Login tests driven by users.json.
 */
public class UserLoginTest extends BaseTest implements ITest {

    private final ThreadLocal<String> currentTestName = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setTestName(Object[] params) {
        if (params != null && params.length > 0 && params[0] instanceof UserData userData) {
            currentTestName.set(userData.getDescription());
        }
    }

    @Override
    public String getTestName() {
        String name = currentTestName.get();
        return (name == null || name.isBlank()) ? getClass().getSimpleName() : name;
    }

    @Test(dataProvider = "jsonUsers", dataProviderClass = UserDataProvider.class)
    public void testLoginFromJson(UserData userData) {
        LoginPage loginPage = new LoginPage(getDriver());

        if (userData.isExpectedSuccess()) {
            InventoryPage inventoryPage = loginPage.login(userData.getUsername(), userData.getPassword());
            Assert.assertTrue(inventoryPage.isLoaded(),
                "Expected successful login for: " + userData.getRole());
            Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
                "Expected to navigate to inventory page.");
        } else {
            loginPage.loginExpectingFailure(userData.getUsername(), userData.getPassword());
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                "Expected login error to be displayed for: " + userData.getRole());
            Assert.assertFalse(loginPage.getErrorMessage().isBlank(),
                "Expected non-empty error message for failed login.");
        }
    }
}
