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

        @Given("the following products")
public void theFollowingProducts(DataTable dataTable) {

    // List all pets and delete them one by one
Response response = given()
        .contentType(ContentType.JSON)
        .when()
        .get(context.getBaseUrl() + "/products");

assertThat(response.getStatusCode()).isEqualTo(200);

List<Product> existingProducts;
try {
    existingProducts = new ObjectMapper().readValue(
            response.asString(),
            new TypeReference<List<Product>>() {}
    );
    
    // Delete each pet
    for (Product product : existingProducts) {
        Response deleteResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(context.getBaseUrl() + "/products/" + product.getId());
        
        assertThat(deleteResponse.getStatusCode()).isEqualTo(204);
    }
} catch (Exception e) {
    throw new RuntimeException("Error processing existing Products", e);
}


    // Load the database with new pets
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
        // Create payload for the POST request
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", row.get("name"));
        payload.put("description", row.get("description"));

        // Convert "available" string to boolean
        String availableStr = row.get("available");
        boolean isAvailable = "True".equalsIgnoreCase(availableStr) || 
                             "true".equalsIgnoreCase(availableStr) || 
                             "1".equals(availableStr);
        payload.put("available", isAvailable);

        payload.put("price", row.get("price"));
        payload.put("category", row.get("category"));

        // Post the new pet to the API
        Response postResponse = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(context.getBaseUrl() + "/products");

        assertThat(postResponse.getStatusCode()).isEqualTo(201);
    }
    
}
    
}
