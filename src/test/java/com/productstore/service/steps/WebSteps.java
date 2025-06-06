package com.productstore.service.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WebSteps {

    private static final String ID_PREFIX = "product_";
    private final TestContext context;
    private final WebDriver driver;
    private final Map<String, String> clipboard = new HashMap<>();
    
    public WebSteps(TestContext context) {
        this.context = context;
        this.driver = context.getDriver();
    }

    @When("I visit the {string}")
    public void iVisitThe(String page) {
        if ("Home Page".equals(page)) {
            driver.get(context.getBaseUrl());
        }
    }

    @Then("I should see {string} in the title")
    public void i_should_see_in_the_title(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertTrue(actualTitle.contains(expectedTitle), 
            "Expected title to contain '" + expectedTitle + "' but was '" + actualTitle + "'");
    }
    
    @Then("I should not see {string}")
    public void i_should_not_see(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertFalse(bodyText.contains(text), 
            "Page should not contain text: " + text);
    }
    
    @When("I set the {string} to {string}")
    public void i_set_the_to(String field, String value) {
        String id = ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(value);
    }
    
    
    
    
    
    @When("I select {string} in the {string} dropdown")
    public void i_select_in_the_dropdown(String value, String field) {
        String id = ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(value);
    }
    
    @Then("I should see {string} in the {string} dropdown")
    public void i_should_see_in_the_dropdown(String value, String field) {
        String id = ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        Select dropdown = new Select(element);
        assertEquals(value, dropdown.getFirstSelectedOption().getText());
    }
    
    @Then("I should see {string} in the {string} field")
    public void i_should_see_in_the_field(String value, String field) {
        String id = ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        
        if (element.getTagName().equals("select")) {
            Select dropdown = new Select(element);
            assertEquals(value, dropdown.getFirstSelectedOption().getText());
        } else {
            assertEquals(value, element.getAttribute("value"));
        }
    }
    
    @When("I copy the {string} field")
    public void i_copy_the_field(String field) {
        String id = field.toLowerCase().equals("id") ? "product_id" : ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        clipboard.put(field, element.getAttribute("value"));
    }
    
    @When("I paste the {string} field")
    public void i_paste_the_field(String field) {
        String id = field.toLowerCase().equals("id") ? "product_id" : ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        element.clear();
        element.sendKeys(clipboard.get(field));
    }
    
    @Then("the {string} field should be empty")
    public void the_field_should_be_empty(String field) {
        String id = field.toLowerCase().equals("id") ? "product_id" : ID_PREFIX + field.toLowerCase();
        WebElement element = driver.findElement(By.id(id));
        assertTrue(element.getAttribute("value").isEmpty());
    }
    
    @When("I change {string} to {string}")
    public void i_change_to(String field, String value) {
        i_set_the_to(field, value);
    }
    
    @When("I press the {string} button")
    public void i_press_the_button(String buttonName) {
        // Updated to match the HTML structure with buttonName-btn format
        String id = buttonName.toLowerCase() + "-btn";
        WebElement button = driver.findElement(By.id(id));
        button.click();
    }

    @Then("I should see {string} in the results")
    public void i_should_see_in_the_results(String text) {
        WebElement results = driver.findElement(By.id("search_results"));
        assertTrue(results.getText().contains(text),
            "Expected results to contain '" + text + "' but was '" + results.getText() + "'");
    }
    
    @Then("I should not see {string} in the results")
    public void i_should_not_see_in_the_results(String text) {
        WebElement results = driver.findElement(By.id("search_results"));
        assertFalse(results.getText().contains(text),
            "Results should not contain text: " + text);
    }

    @Then("I should see the message {string}")
    public void i_should_see_the_message(String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement flash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash_message")));
        assertEquals(message, flash.getText());
    }
    
}
