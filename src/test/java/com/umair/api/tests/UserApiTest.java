package com.umair.api.tests;

import com.umair.api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Full CRUD test coverage for the /users endpoint on reqres.in.
 *
 * Tests:
 *  GET    /users          — list users, pagination
 *  GET    /users/{id}     — single user, not-found case
 *  POST   /users          — create user
 *  PUT    /users/{id}     — full update
 *  PATCH  /users/{id}     — partial update
 *  DELETE /users/{id}     — delete
 */
public class UserApiTest extends BaseApiTest {

    // ── GET ──────────────────────────────────────────────────────────────────

    @Test(description = "GET /users returns 200 and non-empty list",
          groups = {"smoke", "get"})
    public void testGetUsersReturns200() {
        given()
            .spec(spec)
            .queryParam("page", 1)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("data", not(empty()))
            .body("page", equalTo(1))
            .body("data[0].id", notNullValue())
            .body("data[0].email", containsString("@"));
    }

    @Test(description = "GET /users supports pagination",
          groups = {"regression", "get"})
    public void testGetUsersPage2() {
        given()
            .spec(spec)
            .queryParam("page", 2)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("page", equalTo(2))
            .body("data", not(empty()));
    }

    @Test(description = "GET /users/{id} returns correct user",
          groups = {"smoke", "get"})
    public void testGetSingleUserById() {
        int userId = 2;
        given()
            .spec(spec)
        .when()
            .get("/users/{id}", userId)
        .then()
            .statusCode(200)
            .body("data.id",    equalTo(userId))
            .body("data.email", notNullValue())
            .body("data.first_name", notNullValue());
    }

    @Test(description = "GET /users/{id} returns 404 for unknown user",
          groups = {"regression", "negative"})
    public void testGetUserNotFound() {
        given()
            .spec(spec)
        .when()
            .get("/users/{id}", 9999)
        .then()
            .statusCode(404)
            .body(equalTo("{}"));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Test(description = "POST /users creates a user and returns 201",
          groups = {"smoke", "post"})
    public void testCreateUser() {
        User newUser = new User("Umair", "Zahid", "testemail.com");

        Response response =
            given()
                .spec(spec)
                .body(newUser)
            .when()
                .post("/users")
            .then()
                .statusCode(201)
                .body("first_name", equalTo("Umair"))
                .body("last_name",  equalTo("Zahid"))
                .body("id",         notNullValue())
                .body("createdAt",  notNullValue())
                .extract().response();

        // Assert returned ID is a non-empty string
        String createdId = response.jsonPath().getString("id");
        Assert.assertNotNull(createdId, "Created user ID should not be null");
        Assert.assertFalse(createdId.isEmpty(), "Created user ID should not be empty");

        System.out.println("✅ Created user with ID: " + createdId);
    }

    // ── PUT ──────────────────────────────────────────────────────────────────

    @Test(description = "PUT /users/{id} fully updates user and returns 200",
          groups = {"regression", "put"})
    public void testUpdateUserPut() {
        User updated = new User("Umair", "Zahid-Updated", "updated@gmail.com");

        given()
            .spec(spec)
            .body(updated)
        .when()
            .put("/users/{id}", 2)
        .then()
            .statusCode(200)
            .body("last_name",  equalTo("Zahid-Updated"))
            .body("updatedAt",  notNullValue());
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @Test(description = "PATCH /users/{id} partially updates user and returns 200",
          groups = {"regression", "patch"})
    public void testUpdateUserPatch() {
        String patchBody = "{\"job\": \"SDET Lead\"}";

        given()
            .spec(spec)
            .body(patchBody)
        .when()
            .patch("/users/{id}", 2)
        .then()
            .statusCode(200)
            .body("job",       equalTo("SDET Lead"))
            .body("updatedAt", notNullValue());
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Test(description = "DELETE /users/{id} returns 204 No Content",
          groups = {"smoke", "delete"})
    public void testDeleteUser() {
        given()
            .spec(spec)
        .when()
            .delete("/users/{id}", 2)
        .then()
            .statusCode(204);
    }

    // ── RESPONSE TIME ────────────────────────────────────────────────────────

    @Test(description = "GET /users responds within 3 seconds",
          groups = {"performance"})
    public void testResponseTime() {
        given()
            .spec(spec)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .time(lessThan(3000L));
    }
}
