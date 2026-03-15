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

    protected RequestSpecification spec;

    @BeforeSuite
    public void suiteSetup() throws Exception {
        Files.createDirectories(Paths.get("reports"));
        ApiConfig.init();
        spec = ApiConfig.getRequestSpec();
    }
}
