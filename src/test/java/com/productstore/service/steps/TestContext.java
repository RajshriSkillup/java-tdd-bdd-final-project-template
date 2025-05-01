package com.productstore.service.steps;


import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private String baseUrl;
    private int waitSeconds;
    private DataTable petDataTable;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }
    
    public DataTable getPetDataTable() {
        return petDataTable;
    }
    
    public void setPetDataTable(DataTable petDataTable) {
        this.petDataTable = petDataTable;
    }
}
