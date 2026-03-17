package com.lab9.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.pages.InventoryPage;
import com.lab9.pages.LoginPage;
import com.lab9.util.ExcelReader;
import com.lab9.util.TestDataInitializer;

/**
 * Data-driven login tests that read all test cases from {@code login_data.xlsx}.
 *
 * <p>Groups:
 * <ul>
 *   <li>{@code smoke}      – runs only {@link #testSmokeLogin} (SmokeCases sheet)</li>
 *   <li>{@code regression} – runs testSmokeLogin, {@link #testNegativeLogin},
 *                            and {@link #testBoundaryLogin} (all 3 sheets)</li>
 * </ul>
 *
 * <p>Test names in reports come from the {@code description} column in Excel,
 * implemented via the {@link ITest} interface. ThreadLocal is used so parallel
 * execution remains safe.
 *
 * <p>To run smoke suite:     {@code mvn test}
 * <p>To run regression suite: {@code mvn test -Dtestng.suite=testng-regression.xml}
 */
public class LoginDataDrivenTest extends BaseTest implements ITest {

    private static final String EXCEL_PATH = "src/test/resources/testdata/login_data.xlsx";

    /** Stores the current test name per thread for parallel-safe ITest reporting. */
    private final ThreadLocal<String> currentTestName = new ThreadLocal<>();

    // ------------------------------------------------------------------
    // Suite setup: generate Excel file if it doesn't exist yet
    // ------------------------------------------------------------------

    /**
     * Runs once before the suite. Creates {@code login_data.xlsx} with default
     * test data when the file is missing so the DataProviders can read it.
     * If the file already exists it is left untouched.
     */
    @BeforeSuite(alwaysRun = true)
    public void initTestData() {
        TestDataInitializer.ensureExists(EXCEL_PATH);
    }

    // ------------------------------------------------------------------
    // Test name from Excel description column
    // ------------------------------------------------------------------

    /**
     * Picks up the {@code description} parameter (always the last element) from
     * each DataProvider row and stores it so {@link #getTestName()} can expose it
     * to the TestNG reporting engine.
     *
     * @param params current test method's DataProvider arguments
     */
    @BeforeMethod(alwaysRun = true)
    public void setTestNameFromDescription(Object[] params) {
        if (params != null && params.length > 0) {
            currentTestName.set(String.valueOf(params[params.length - 1]));
        }
    }

    /**
     * Returns the human-readable test name based on the Excel {@code description}
     * column. TestNG calls this method when constructing report entries.
     */
    @Override
    public String getTestName() {
        String name = currentTestName.get();
        return (name != null && !name.isEmpty()) ? name : getClass().getSimpleName();
    }

    // ------------------------------------------------------------------
    // DataProviders
    // ------------------------------------------------------------------

    /**
     * Provides rows from the {@code SmokeCases} sheet.
     * Columns: username | password | expected_url | description
     */
    @DataProvider(name = "smokeCases")
    public Object[][] smokeCases() {
        List<Map<String, String>> rows = ExcelReader.readSheet(EXCEL_PATH, "SmokeCases");
        return rows.stream()
            .map(r -> new Object[]{
                r.get("username"),
                r.get("password"),
                r.get("expected_url"),
                r.get("description")
            })
            .toArray(Object[][]::new);
    }

    /**
     * Provides rows from the {@code NegativeCases} sheet.
     * Columns: username | password | expected_error | description
     */
    @DataProvider(name = "negativeCases")
    public Object[][] negativeCases() {
        List<Map<String, String>> rows = ExcelReader.readSheet(EXCEL_PATH, "NegativeCases");
        return rows.stream()
            .map(r -> new Object[]{
                r.get("username"),
                r.get("password"),
                r.get("expected_error"),
                r.get("description")
            })
            .toArray(Object[][]::new);
    }

    /**
     * Provides rows from the {@code BoundaryCases} sheet.
     * Columns: username | password | expected_error | description
     */
    @DataProvider(name = "boundaryCases")
    public Object[][] boundaryCases() {
        List<Map<String, String>> rows = ExcelReader.readSheet(EXCEL_PATH, "BoundaryCases");
        return rows.stream()
            .map(r -> new Object[]{
                r.get("username"),
                r.get("password"),
                r.get("expected_error"),
                r.get("description")
            })
            .toArray(Object[][]::new);
    }

    // ------------------------------------------------------------------
    // Test methods
    // ------------------------------------------------------------------

    /**
     * Smoke + regression: verifies that valid credentials redirect to the
     * inventory page whose URL matches {@code expected_url} from the spreadsheet.
     *
     * @param username    SauceDemo username
     * @param password    SauceDemo password
     * @param expectedUrl URL fragment that the post-login page must contain
     * @param description human-readable scenario name (used as test name in report)
     */
    @Test(groups = {"smoke", "regression"}, dataProvider = "smokeCases")
    public void testSmokeLogin(String username, String password,
                               String expectedUrl, String description) {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(),
            "[" + description + "] Inventory page should be loaded after successful login.");
        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"),
            "[" + description + "] Current URL should contain 'inventory'. Actual: "
                + getDriver().getCurrentUrl());
    }

    /**
     * Regression only: verifies that invalid credentials display the exact error
     * message stored in the {@code expected_error} column.
     *
     * @param username      invalid or empty username
     * @param password      invalid or empty password
     * @param expectedError partial or full error message expected in the UI
     * @param description   human-readable scenario name
     */
    @Test(groups = "regression", dataProvider = "negativeCases")
    public void testNegativeLogin(String username, String password,
                                  String expectedError, String description) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "[" + description + "] Error message element should be visible.");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
            "[" + description + "] Error message mismatch."
                + " Expected to contain: [" + expectedError + "]"
                + " Actual: [" + loginPage.getErrorMessage() + "]");
    }

    /**
     * Regression only: verifies boundary-value inputs produce the expected error
     * from the {@code BoundaryCases} sheet.
     *
     * @param username      boundary username value
     * @param password      boundary password value
     * @param expectedError partial or full error message expected in the UI
     * @param description   human-readable scenario name
     */
    @Test(groups = "regression", dataProvider = "boundaryCases")
    public void testBoundaryLogin(String username, String password,
                                  String expectedError, String description) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(username, password);

        Assert.assertTrue(loginPage.isErrorDisplayed(),
            "[" + description + "] Error message element should be visible.");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
            "[" + description + "] Error message mismatch."
                + " Expected to contain: [" + expectedError + "]"
                + " Actual: [" + loginPage.getErrorMessage() + "]");
    }
}
