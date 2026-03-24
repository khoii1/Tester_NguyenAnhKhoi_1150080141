package lab10.api;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserGetApiTest extends ApiBaseTest {

    @Test(description = "GET /api/users?page=1 - Verify pagination basics and non-empty data")
    public void testGetUsersPage1() {
        given()
                .spec(requestSpec)
                .queryParam("page", 1)
        .when()
                .get("/api/users")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0))
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test(description = "GET /api/users?page=2 - Verify required fields for each user")
    public void testGetUsersPage2() {
        given()
                .spec(requestSpec)
                .queryParam("page", 2)
        .when()
                .get("/api/users")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", hasSize(greaterThan(0)))
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue());
    }

    @Test(description = "GET /api/users/3 - Verify user details and email format")
    public void testGetSingleUserById3() {
        given()
                .spec(requestSpec)
        .when()
                .get("/api/users/3")
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id", equalTo(3))
                .body("data.email", allOf(matchesPattern("^[A-Za-z0-9._%+-]+@reqres\\.in$"), endsWith("@reqres.in")))
                .body("data.first_name", allOf(notNullValue(), not(emptyString())));
    }

    @Test(description = "GET /api/users/9999 - Verify not found response is empty object")
    public void testGetUserNotFound() {
        given()
                .spec(requestSpec)
        .when()
                .get("/api/users/9999")
        .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("$", equalTo(new java.util.HashMap<>()));
    }
}
