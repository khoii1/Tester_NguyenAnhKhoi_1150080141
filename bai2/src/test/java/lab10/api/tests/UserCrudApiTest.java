package lab10.api.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import lab10.api.base.ApiBaseTest;
import lab10.api.models.CreateUserRequest;
import lab10.api.models.UserResponse;

public class UserCrudApiTest extends ApiBaseTest {

    @Test(description = "A. POST /api/users - Tao user moi va xac nhan field tra ve")
    public void shouldCreateUserSuccessfully() {
        CreateUserRequest request = new CreateUserRequest("Nguyen Van A", "Tester");

        UserResponse response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo(request.getName()))
                .body("job", equalTo(request.getJob()))
                .body("id", not(emptyOrNullString()))
                .body("createdAt", not(emptyOrNullString()))
                .extract()
                .as(UserResponse.class);

        assertThat(response.getId(), not(emptyOrNullString()));
        assertThat(response.getCreatedAt(), not(emptyOrNullString()));
    }

    @Test(description = "B. PUT /api/users/2 - Cap nhat toan bo user")
    public void shouldUpdateUserByPutSuccessfully() {
        CreateUserRequest createRequest = new CreateUserRequest("Nguyen Van A", "Tester");
        UserResponse createdUser = given()
                .spec(requestSpec)
                .body(createRequest)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);

        CreateUserRequest updateRequest = new CreateUserRequest("Nguyen Van A", "Senior Tester");
        UserResponse updatedUser = given()
                .spec(requestSpec)
                .body(updateRequest)
                .when()
                .put("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", equalTo(updateRequest.getName()))
                .body("job", equalTo(updateRequest.getJob()))
                .body("updatedAt", notNullValue())
                .extract()
                .as(UserResponse.class);

        assertThat(updatedUser.getUpdatedAt(), not(emptyOrNullString()));
        if (createdUser.getCreatedAt() != null && !createdUser.getCreatedAt().isBlank()) {
            assertThat(updatedUser.getUpdatedAt(), not(equalTo(createdUser.getCreatedAt())));
        }
    }

    @Test(description = "C. PATCH /api/users/2 - Cap nhat mot phan (chi field job)")
    public void shouldPatchUserPartiallySuccessfully() {
        CreateUserRequest patchRequest = new CreateUserRequest(null, "Lead Tester");

        UserResponse patchedUser = given()
                .spec(requestSpec)
                .body(patchRequest)
                .when()
                .patch("/api/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Lead Tester"))
                .body("updatedAt", notNullValue())
                .extract()
                .as(UserResponse.class);

        assertThat(patchedUser.getJob(), equalTo("Lead Tester"));
        assertThat(patchedUser.getUpdatedAt(), not(emptyOrNullString()));
        // Request PATCH chi gui field job, nen body response khong duoc thay doi ngoai y nghia truong da gui.
        assertThat(patchedUser.getName(), anyOf(is((String) null), is("")));
    }

    @Test(description = "D. DELETE /api/users/2 - Xoa user")
    public void shouldDeleteUserSuccessfully() {
        String responseBody = given()
                .spec(requestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .extract()
                .asString();

        assertThat(responseBody, equalTo(""));
    }

    @Test(description = "E. Chain API call POST -> GET xac nhan")
    public void shouldChainPostThenGetConfirmation() {
        CreateUserRequest createRequest = new CreateUserRequest("Nguyen Van A", "Tester");

        UserResponse createdUser = given()
                .spec(requestSpec)
                .body(createRequest)
                .when()
                .post("/api/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .extract()
                .as(UserResponse.class);

        String createdId = createdUser.getId();
        assertThat(createdId, not(emptyOrNullString()));
        int createdUserId = Integer.parseInt(createdId);

        Response getResponse = given()
                .spec(requestSpec)
                .when()
                .get("/api/users/{id}", createdId)
                .andReturn();

        // Reqres la mock API: POST tao id moi nhung du lieu khong duoc persistence nhu he thong that.
        // Van chain API call day du (luu id tu POST va goi GET theo id do) de the hien ky thuat theo de bai.
        if (getResponse.statusCode() != 200) {
            throw new SkipException("Reqres mock API khong luu user moi tao de GET theo id vua tao; skip assert persistence.");
        }

        getResponse.then()
                .spec(responseSpec)
                .statusCode(200)
            .body("data.id", equalTo(createdUserId))
                .body("data", notNullValue());
    }
}
