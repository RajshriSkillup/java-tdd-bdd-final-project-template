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

        @Test
public void testGetProduct() throws Exception {
    // Create a product for testing
    Product product = createProductFixture();
    
    // Mock the service method
    when(productService.findById(product.getId())).thenReturn(product);
    
    // Make the request and validate
    mockMvc.perform(get("/products/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(product.getId()))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.price").value(product.getPrice().doubleValue()))
            .andExpect(jsonPath("$.available").value(product.getAvailable()))
            .andExpect(jsonPath("$.category").value(product.getCategory().toString()));
            
    verify(productService).findById(product.getId());
}
    @Test
public void testGetProductNotFound() throws Exception {
    // Mock the service to throw an exception
    when(productService.findById(99L))
            .thenThrow(new ProductNotFoundException("Product with ID 99 not found"));
    
    // Make the request and validate
    mockMvc.perform(get("/products/99")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Product with ID 99 not found"));
            
    verify(productService).findById(99L);
}
    @Test
public void testUpdateProduct() throws Exception {
    // Create original product
    Product product = createProductFixture();
    
    // Create updated product
    Product updatedProduct = new Product();
    updatedProduct.setId(product.getId());
    updatedProduct.setName(product.getName());
    updatedProduct.setDescription("Updated description");
    updatedProduct.setPrice(product.getPrice());
    updatedProduct.setAvailable(product.getAvailable());
    updatedProduct.setCategory(product.getCategory());
    
    // Mock service behavior
    when(productService.update(eq(product.getId()), any(Product.class))).thenReturn(updatedProduct);
    
    // Make the request and validate
    mockMvc.perform(put("/products/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedProduct)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(updatedProduct.getId()))
            .andExpect(jsonPath("$.description").value("Updated description"));
            
    verify(productService).update(eq(product.getId()), any(Product.class));
}

    @Test
public void testDeleteProduct() throws Exception {
    // Create a product
    Product product = createProductFixture();
    
    // Mock service behavior
    doNothing().when(productService).delete(product.getId());
    
    // Make the request and validate
    mockMvc.perform(delete("/products/" + product.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
            
    verify(productService).delete(product.getId());
}

    @Test
public void testListAllProducts() throws Exception {
    // Create product list
    List<Product> productList = Arrays.asList(
        createProductFixture(),
        createProductFixture(),
        createProductFixture()
    );
    
    // Mock service behavior
    when(productService.findAll()).thenReturn(productList);
    
    // Make the request and validate
    mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[1].id").exists())
            .andExpect(jsonPath("$[2].id").exists());
            
    verify(productService).findAll();
}

    @Test
public void testListProductsByName() throws Exception {
    // Create products with specific name
    String testName = "TestProduct";
    List<Product> products = Arrays.asList(
        createProductFixture(),
        createProductFixture()
    );
    products.forEach(p -> p.setName(testName));
    
    // Mock service behavior
    when(productService.findByName(testName)).thenReturn(products);
    
    // Make the request and validate
    mockMvc.perform(get("/products")
            .param("name", testName)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name").value(testName))
            .andExpect(jsonPath("$[1].name").value(testName));
            
    verify(productService).findByName(testName);
}

    @Test
public void testListProductsByCategory() throws Exception {
    // Create products with specific category
    Product.Category testCategory = Product.Category.FOOD;
    List<Product> products = Arrays.asList(
        createProductFixture(),
        createProductFixture()
    );
    products.forEach(p -> p.setCategory(testCategory));
    
    // Mock service behavior
    when(productService.findByCategory(testCategory)).thenReturn(products);
    
    // Make the request and validate
    mockMvc.perform(get("/products")
            .param("category", testCategory.toString())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].category").value(testCategory.toString()))
            .andExpect(jsonPath("$[1].category").value(testCategory.toString()));
            
    verify(productService).findByCategory(testCategory);
}
    @Test
public void testListProductsByAvailability() throws Exception {
    // Create products with specific availability
    boolean isAvailable = true;
    List<Product> products = Arrays.asList(
        createProductFixture(),
        createProductFixture()
    );
    products.forEach(p -> p.setAvailable(isAvailable));
    
    // Mock service behavior
    when(productService.findByAvailability(isAvailable)).thenReturn(products);
    
    // Make the request and validate
    mockMvc.perform(get("/products")
            .param("available", String.valueOf(isAvailable))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].available").value(isAvailable))
            .andExpect(jsonPath("$[1].available").value(isAvailable));
            
    verify(productService).findByAvailability(isAvailable);
}


   
}