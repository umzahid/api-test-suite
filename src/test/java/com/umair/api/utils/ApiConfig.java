package com.umair.api.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Centralized RestAssured configuration.
 * Sets base URI, default headers, logging, and shared request/response specs.
 */
public class ApiConfig {

    public static final String BASE_URL     = "https://reqres.in/api";
    public static final int    TIMEOUT_MS   = 10_000;
    public static final String LOG_DIR      = "reports/api-logs.txt";

    private static RequestSpecification  requestSpec;
    private static ResponseSpecification responseSpec;

    public static void init() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        try {
            PrintStream logStream = new PrintStream(new FileOutputStream(LOG_DIR, true));
            requestSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter(logStream))
                    .addFilter(new ResponseLoggingFilter(logStream))
                    .build();
        } catch (Exception e) {
            // fallback without file logging
            requestSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .build();
        }

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpec() {
        if (requestSpec == null) init();
        return requestSpec;
    }

    public static ResponseSpecification getResponseSpec() {
        if (responseSpec == null) init();
        return responseSpec;
    }
}
