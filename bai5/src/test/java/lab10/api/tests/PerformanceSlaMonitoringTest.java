package lab10.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lab10.api.base.ApiBaseTest;
import lab10.api.models.CreateUserRequest;

public class PerformanceSlaMonitoringTest extends ApiBaseTest {

    @DataProvider(name = "slaScenarios")
    public Object[][] slaScenarios() {
        return new Object[][] {
                { "GET", "/users", 2000L, 200 },
                { "GET", "/users/2", 1500L, 200 },
                { "POST", "/users", 3000L, 201 },
                { "POST", "/login", 2000L, 200 },
                { "DELETE", "/users/2", 1000L, 204 }
        };
    }

    @Test(dataProvider = "slaScenarios", description = "Validate SLA and functional assertion for key reqres APIs")
    public void shouldValidateSlaByScenario(String method, String endpoint, long maxMs, int expectedStatus) {
        Response response = callApiForSla(method, endpoint, maxMs);

        long actualMs = response.time();
        System.out.printf("[SLA] %s %s -> actual: %d ms, threshold: %d ms%n", method, endpoint, actualMs, maxMs);

        assertThat("Response time must satisfy SLA", actualMs, lessThan(maxMs));
        assertThat("Status code must match expected", response.statusCode(), equalTo(expectedStatus));

        switch (method + " " + endpoint) {
            case "GET /users":
                response.then().body("data.size()", greaterThanOrEqualTo(1));
                break;
            case "GET /users/2":
                response.then().body("data.id", equalTo(2));
                break;
            case "POST /users":
                response.then().body("id", not(isEmptyOrNullString()));
                break;
            case "POST /login":
                response.then().body("token", not(isEmptyOrNullString()));
                break;
            case "DELETE /users/2":
                assertThat(response.asString(), equalTo(""));
                break;
            default:
                throw new IllegalArgumentException("Unsupported scenario: " + method + " " + endpoint);
        }
    }

    @Step("Gọi {method} {endpoint} - SLA: {maxMs}ms")
    public Response callApiForSla(String method, String endpoint, long maxMs) {
        String apiEndpoint = "/api" + endpoint;

        switch (method) {
            case "GET":
                return given()
                        .spec(requestSpec)
                        .when()
                        .get(apiEndpoint)
                        .then()
                        .spec(responseSpec)
                        .extract()
                        .response();
            case "POST":
                Object body;
                if ("/users".equals(endpoint)) {
                    body = new CreateUserRequest("Nguyen Van A", "Tester");
                } else if ("/login".equals(endpoint)) {
                    Map<String, String> loginBody = new HashMap<>();
                    loginBody.put("email", "eve.holt@reqres.in");
                    loginBody.put("password", "cityslicka");
                    body = loginBody;
                } else {
                    throw new IllegalArgumentException("Unsupported POST endpoint: " + endpoint);
                }

                return given()
                        .spec(requestSpec)
                        .body(body)
                        .when()
                        .post(apiEndpoint)
                        .then()
                        .spec(responseSpec)
                        .extract()
                        .response();
            case "DELETE":
                return given()
                        .spec(requestSpec)
                        .when()
                        .delete(apiEndpoint)
                        .then()
                        .extract()
                        .response();
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    @Test(description = "Simple SLA monitoring over 10 consecutive runs")
    public void testSimpleSlaMonitoringOver10Runs() {
        List<Long> responseTimes = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Response response = given()
                    .spec(requestSpec)
                    .when()
                    .get("/api/users/2")
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .body("data.id", equalTo(2))
                    .extract()
                    .response();

            long current = response.time();
            responseTimes.add(current);
            System.out.printf("[MONITOR] Run %d - GET /users/2: %d ms%n", i, current);
        }

        long min = Collections.min(responseTimes);
        long max = Collections.max(responseTimes);
        double average = responseTimes.stream().mapToLong(Long::longValue).average().orElse(0.0D);

        System.out.printf("[MONITOR] GET /users/2 over 10 runs -> average: %.2f ms, min: %d ms, max: %d ms%n",
                average, min, max);
    }
}
