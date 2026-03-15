package com.umair.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Authentication endpoint tests — POST /login, POST /register.
 */
public class AuthApiTest extends BaseApiTest {

    @BeforeSuite
    public void setup() throws Exception {
        BaseApiTest.suiteSetup();
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────────

    @Test(description = "POST /login with valid credentials returns token",
          groups = {"smoke", "auth"})
    public void testSuccessfulLogin() {
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

        String token =
            given()
                .spec(spec)
                .body(body)
            .when()
                .post("/login")
            .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");

        Assert.assertNotNull(token, "Auth token should be present");
        Assert.assertFalse(token.isEmpty(), "Auth token should not be empty");
        System.out.println("✅ Received token: " + token);
    }

    @Test(description = "POST /login without password returns 400",
          groups = {"regression", "negative", "auth"})
    public void testLoginMissingPassword() {
        String body = "{\"email\":\"peter@klaven.com\"}";

        given()
            .spec(spec)
            .body(body)
        .when()
            .post("/login")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    // ── REGISTER ──────────────────────────────────────────────────────────────

    @Test(description = "POST /register with valid payload returns id and token",
          groups = {"smoke", "auth"})
    public void testSuccessfulRegister() {
        String body = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";

        given()
            .spec(spec)
            .body(body)
        .when()
            .post("/register")
        .then()
            .statusCode(200)
            .body("id",    notNullValue())
            .body("token", notNullValue());
    }

    @Test(description = "POST /register without password returns 400",
          groups = {"regression", "negative", "auth"})
    public void testRegisterMissingPassword() {
        String body = "{\"email\":\"sydney@fife.com\"}";

        given()
            .spec(spec)
            .body(body)
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("Missing password"));
    }

    @Test(description = "POST /register with unrecognised email returns 400",
          groups = {"regression", "negative", "auth"})
    public void testRegisterUnknownEmail() {
        String body = "{\"email\":\"unknown@test.com\",\"password\":\"test123\"}";

        given()
            .spec(spec)
            .body(body)
        .when()
            .post("/register")
        .then()
            .statusCode(400)
            .body("error", notNullValue());
    }
}
