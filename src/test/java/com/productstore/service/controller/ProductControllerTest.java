package com.productstore.service.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productstore.service.exception.ProductNotFoundException;
import com.productstore.service.model.Product;
import com.productstore.service.service.ProductService;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Product testProduct;
    
    @BeforeEach
    public void setUp() {
        testProduct = createProductFixture();
    }
    
    private Product createProductFixture() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("29.99"));
        product.setAvailable(true);
        product.setCategory(Product.Category.CLOTHS);
        return product;
    }

   

    @Test
    public void testCreateProduct() throws Exception {
        // Create a product for testing
        Product product = createProductFixture();
        product.setId(null); // ID should be null for creation
        
        // Create the expected result (product with ID assigned)
        Product createdProduct = createProductFixture();
        createdProduct.setId(1L);
        
        // Mock service behavior
        when(productService.create(any(Product.class))).thenReturn(createdProduct);
        
        // Make the request and validate
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/products/1")))
                .andExpect(jsonPath("$.id").value(createdProduct.getId()))
                .andExpect(jsonPath("$.name").value(createdProduct.getName()))
                .andExpect(jsonPath("$.description").value(createdProduct.getDescription()))
                .andExpect(jsonPath("$.price").value(createdProduct.getPrice().doubleValue()))
                .andExpect(jsonPath("$.available").value(createdProduct.getAvailable()))
                .andExpect(jsonPath("$.category").value(createdProduct.getCategory().toString()));
                
        verify(productService).create(any(Product.class));
    }

    @Test
    public void testCreateProductWithValidationError() throws Exception {
        // Create an invalid product (missing required fields)
        Product invalidProduct = new Product();
        // Name and description are required but not set
        
        // Make the request and validate
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
                
        // Verify service was never called with invalid data
        verify(productService, never()).create(any(Product.class));
    }

    
   
}