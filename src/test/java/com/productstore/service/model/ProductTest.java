package com.productstore.service.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.productstore.service.factory.ProductFactory;
import com.productstore.service.repository.ProductRepository;

@DataJpaTest
public class ProductTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ProductRepository productRepository;
    
    @BeforeEach
    public void setUp() {
        // Clear database before each test
        productRepository.deleteAll();
    }
    
    
    @Test
public void testReadProduct() {
    // Create a product 
    Product product = ProductFactory.createProduct();
    product.setId(null);
    
    // Save it to the database
    productRepository.save(product);
    
    // Verify it was assigned an ID
    assertNotNull(product.getId());
    
    // Fetch it back from database
    Product foundProduct = productRepository.findById(product.getId()).orElse(null);
    
    // Verify the data matches
    assertNotNull(foundProduct);
    assertEquals(product.getId(), foundProduct.getId());
    assertEquals(product.getName(), foundProduct.getName());
    assertEquals(product.getDescription(), foundProduct.getDescription());
    assertEquals(0, product.getPrice().compareTo(foundProduct.getPrice()));
    assertEquals(product.getAvailable(), foundProduct.getAvailable());
    assertEquals(product.getCategory(), foundProduct.getCategory());
}

   @Test
public void testUpdateProduct() {
    // Create a product
    Product product = ProductFactory.createProduct();
    product.setId(null);
    
    // Save it to the database
    productRepository.save(product);
    assertNotNull(product.getId());
    
    // Change it and save it
    String newDescription = "Testing update functionality";
    Long originalId = product.getId();
    product.setDescription(newDescription);
    productRepository.save(product);
    
    // Verify the changes
    assertEquals(originalId, product.getId());
    assertEquals(newDescription, product.getDescription());
    
    // Fetch it back and make sure it has the updated data
    Product updatedProduct = productRepository.findById(originalId).orElse(null);
    assertNotNull(updatedProduct);
    assertEquals(originalId, updatedProduct.getId());
    assertEquals(newDescription, updatedProduct.getDescription());
}

    @Test
public void testDeleteProduct() {
    // Create a product
    Product product = ProductFactory.createProduct();
    
    // Save it to the database
    productRepository.save(product);
    
    // Make sure it exists
    assertNotNull(productRepository.findById(product.getId()).orElse(null));
    
    // Delete it
    productRepository.delete(product);
    
    // Make sure it's gone
    assertNull(productRepository.findById(product.getId()).orElse(null));
}

    @Test
public void testListAllProducts() {
    // Make sure the database is empty
    productRepository.deleteAll();
    assertEquals(0, productRepository.count());
    
    // Create 5 products
    for (int i = 0; i < 5; i++) {
        Product product = ProductFactory.createProduct();
        productRepository.save(product);
    }
    
    // Verify we get 5 products back
    List<Product> products = productRepository.findAll();
    assertEquals(5, products.size());
}

     @Test
public void testFindByName() {
    // Create a batch of products with varying names
    List<Product> testProducts = ProductFactory.createProductList(10);
    testProducts.forEach(productRepository::save);
    
    // Get the name of one product
    String testName = testProducts.get(0).getName();
    
    // Count how many products have this name
    long expectedCount = testProducts.stream()
            .filter(p -> p.getName().equals(testName))
            .count();
    
    // Retrieve products by name
    List<Product> foundProducts = productRepository.findByName(testName);
    
    // Verify count
    assertEquals(expectedCount, foundProducts.size());
    
    // Verify all returned products have the expected name
    for (Product product : foundProducts) {
        assertEquals(testName, product.getName());
    }
}

      @Test
public void testFindByAvailability() {
    // Create a batch of products
    List<Product> testProducts = ProductFactory.createProductList(10);
    testProducts.forEach(productRepository::save);
    
    // Choose availability status (true)
    boolean isAvailable = true;
    
    // Count products with this availability
    long expectedCount = testProducts.stream()
            .filter(p -> p.getAvailable() == isAvailable)
            .count();
    
    // Retrieve products by availability
    List<Product> foundProducts = productRepository.findByAvailable(isAvailable);
    
    // Verify count
    assertEquals(expectedCount, foundProducts.size());
    
    // Verify all returned products have the expected availability
    for (Product product : foundProducts) {
        assertEquals(isAvailable, product.getAvailable());
    }
}

      @Test
public void testFindByCategory() {
    // Create a batch of products
    List<Product> testProducts = ProductFactory.createProductList(10);
    testProducts.forEach(productRepository::save);
    
    // Choose a category
    Product.Category testCategory = testProducts.get(0).getCategory();
    
    // Count products with this category
    long expectedCount = testProducts.stream()
            .filter(p -> p.getCategory() == testCategory)
            .count();
    
    // Retrieve products by category
    List<Product> foundProducts = productRepository.findByCategory(testCategory);
    
    // Verify count
    assertEquals(expectedCount, foundProducts.size());
    
    // Verify all returned products have the expected category
    for (Product product : foundProducts) {
        assertEquals(testCategory, product.getCategory());
    }
}


}