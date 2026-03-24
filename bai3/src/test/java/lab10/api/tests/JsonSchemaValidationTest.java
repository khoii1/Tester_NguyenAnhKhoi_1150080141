package lab10.api.tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import lab10.api.base.ApiBaseTest;
import lab10.api.models.CreateUserRequest;

public class JsonSchemaValidationTest extends ApiBaseTest {

    @Test(description = "A. Validate schema for GET /api/users?page=1")
    public void shouldValidateUserListSchema() {
        given()
                .spec(requestSpec)
                .queryParam("page", 1)
                .when()
                .get("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"));
    }

    @Test(description = "B. Validate schema for GET /api/users/2")
    public void shouldValidateSingleUserSchema() {
        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test(description = "C. Validate schema for POST /api/users")
    public void shouldValidateCreateUserSchema() {
        CreateUserRequest requestBody = new CreateUserRequest("Nguyen Van A", "Tester");

        given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }

    @Test(
            description = "Demo FAIL: Use invalid schema to prove schema validation catches missing field",
            enabled = false)
    public void demoFailWithInvalidSchema() {
        // Demo fail theo de bai: schema invalid bat buoc field khong ton tai trong response.
        // Bat enabled=true de xem test fail va thong bao loi schema validation.
        given()
                .spec(requestSpec)
                .when()
                .get("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema-invalid.json"));
    }
}
