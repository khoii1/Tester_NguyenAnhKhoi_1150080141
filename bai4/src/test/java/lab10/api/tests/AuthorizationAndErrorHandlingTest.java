package lab10.api.tests;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import lab10.api.base.ApiBaseTest;

public class AuthorizationAndErrorHandlingTest extends ApiBaseTest {

    @Test(description = "A1. Login thanh cong")
    public void shouldLoginSuccessfully() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "cityslicka");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", not(isEmptyString()));
    }

    @Test(description = "A2. Login thieu password")
    public void shouldReturnErrorWhenLoginMissingPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test(description = "A3. Login thieu email")
    public void shouldReturnErrorWhenLoginMissingEmail() {
        Map<String, String> body = new HashMap<>();
        body.put("password", "cityslicka");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }

    @Test(description = "A4. Register thanh cong")
    public void shouldRegisterSuccessfully() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "eve.holt@reqres.in");
        body.put("password", "pistol");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue())
                .body("token", not(isEmptyString()));
    }

    @Test(description = "A5. Register thieu password")
    public void shouldReturnErrorWhenRegisterMissingPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "sydney@fife");

        given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
                { "eve.holt@reqres.in", "cityslicka", 200, null },
                { "eve.holt@reqres.in", "", 400, "Missing password" },
                { "", "cityslicka", 400, "Missing email or username" },
                { "notexist@reqres.in", "wrongpass", 400, "user not found" },
                { "invalid-email", "pass123", 400, "user not found" }
        };
    }

    @Test(dataProvider = "loginScenarios", description = "B. Data-driven login error handling")
    public void shouldHandleLoginScenarios(String email, String password, int expectedStatus, String expectedError) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);

        if (password != null && !password.isEmpty()) {
            body.put("password", password);
        }

        if (expectedError != null) {
            given()
                    .spec(requestSpec)
                    .body(body)
                    .when()
                    .post("/api/login")
                    .then()
                    .spec(responseSpec)
                    .statusCode(expectedStatus)
                    .body("error", containsString(expectedError));
        } else {
            given()
                    .spec(requestSpec)
                    .body(body)
                    .when()
                    .post("/api/login")
                    .then()
                    .spec(responseSpec)
                    .statusCode(expectedStatus)
                    .body("token", notNullValue())
                    .body("token", not(isEmptyString()));
        }
    }
}
