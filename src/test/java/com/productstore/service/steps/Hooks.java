package com.productstore.service.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class Hooks {
    private TestContext context;
    
    public Hooks(TestContext context) {
        this.context = context;
    }
    
    @Before
    public void setup() {
        // Set up environment variables
        String baseUrl = System.getenv("BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:8080";
        }
        context.setBaseUrl(baseUrl);
        
        String waitSecondsStr = System.getenv("WAIT_SECONDS");
        int waitSeconds = 60; // Default
        if (waitSecondsStr != null && !waitSecondsStr.isEmpty()) {
            try {
                waitSeconds = Integer.parseInt(waitSecondsStr);
            } catch (NumberFormatException e) {
                // Use default value
            }
        }
        context.setWaitSeconds(waitSeconds);
        
        // Initialize WebDriver
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless"); // Changed from addArgument to addArguments
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSeconds));
        context.setDriver(driver);
    }
    
    @After
    public void tearDown() {
        // Clean up resources
        if (context.getDriver() != null) {
            context.getDriver().quit();
        }
    }
}
