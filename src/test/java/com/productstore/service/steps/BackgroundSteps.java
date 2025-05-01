package com.productstore.service.steps;


import java.util.List;
import java.util.Map;


import com.productstore.service.model.Product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.cucumber.datatable.DataTable;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BackgroundSteps {
    private final TestContext context;

    public BackgroundSteps(TestContext context) {
        this.context = context;
    }

        
    
}
