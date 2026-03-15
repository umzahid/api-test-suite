package com.umair.api.tests;

import com.umair.api.utils.ApiConfig;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Base class for all API test classes.
 * Initialises RestAssured config and creates output dirs before the suite runs.
 */
public abstract class BaseApiTest {

    protected static RequestSpecification spec;

    static {
        try {
            System.out.println("BaseApiTest static initializer called");
            Files.createDirectories(Paths.get("reports"));
            ApiConfig.init();
            spec = ApiConfig.getRequestSpec();
            System.out.println("spec initialized: " + (spec != null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
