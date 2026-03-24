package lab10.api.tests;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lab10.api.base.BaseTest;
import lab10.api.pages.CartPage;
import lab10.api.pages.InventoryPage;
import lab10.api.pages.LoginPage;

public class ApiUiIntegrationTest extends BaseTest {

    private boolean apiLoginPassed;
    private String apiToken;
    private boolean isApiAlive;

    @BeforeMethod(alwaysRun = true)
    public void setupApiPreconditionLogin() {
        apiLoginPassed = false;
        apiToken = null;

        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "cityslicka");

        // API precondition: call reqres login to get token
        Response loginResponse = given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/login")
                .andReturn();

        if (loginResponse.statusCode() == 200) {
            JsonPath json = loginResponse.jsonPath();
            apiToken = json.getString("token");
            apiLoginPassed = apiToken != null && !apiToken.isBlank();
        }

        System.out.println("[API] Precondition token = " + apiToken);
    }

    @Test(description = "UI login only runs when API precondition passed")
    public void testUiLoginOnlyWhenApiPreconditionPassed() {
        if (!apiLoginPassed) {
            throw new SkipException("API precondition failed");
        }

        LoginPage loginPage = new LoginPage(driver);

        // UI action: open login page and login by form
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        // Assertion: URL and title after successful login
        assertThat(driver.getCurrentUrl(), containsString("inventory"));
        assertThat(driver.getTitle(), equalTo("Swag Labs"));
    }

    @Test(description = "Full API + UI flow with skip when API unavailable")
    public void testFullApiUiFlowWithSkipWhenApiUnavailable() {
        // API check: verify reqres API is alive before UI flow
        Response aliveResponse = given()
                .spec(requestSpec)
                .when()
                .get("/api/users")
                .andReturn();

        isApiAlive = aliveResponse.statusCode() == 200;
        System.out.println("[API] isApiAlive = " + isApiAlive);

        if (!isApiAlive) {
            throw new SkipException("Reqres API is not alive");
        }

        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver, wait);
        CartPage cartPage = new CartPage(driver, wait);

        // UI action: login and add two products
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        assertThat(inventoryPage.isLoaded(), equalTo(true));
        inventoryPage.addFirstTwoItemsToCart();

        // Assertion: cart badge must be 2
        assertThat(inventoryPage.getCartBadgeCount(), equalTo(2));

        // UI action: go to cart
        inventoryPage.goToCart();

        // Assertion: cart must contain exactly 2 items
        assertThat(cartPage.getItemCount(), equalTo(2));
    }
}
