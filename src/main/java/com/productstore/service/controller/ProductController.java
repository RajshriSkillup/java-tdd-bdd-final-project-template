package com.productstore.service.controller;

import com.productstore.service.model.Product;
import com.productstore.service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.productstore.service.exception.ProductNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a Product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.create(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @GetMapping("/{productId}")
public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
    Product product = productService.findById(productId);
    if (product == null) {
        throw new ProductNotFoundException("Product with id '" + productId + "' was not found.");
    }
    return ResponseEntity.ok(product);
}
@PutMapping("/{productId}")
public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @Valid @RequestBody Product product) {
    Product updatedProduct = productService.update(productId, product);
    return ResponseEntity.ok(updatedProduct);
}

@DeleteMapping("/{productId}")
public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
    productService.delete(productId);
    return ResponseEntity.noContent().build();
}

@GetMapping
public ResponseEntity<List<Product>> listProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Boolean available) {
    
    List<Product> products;
    
    if (name != null && !name.isEmpty()) {
        products = productService.findByName(name);
    } else if (category != null && !category.isEmpty()) {
        try {
            Product.Category categoryEnum = Product.Category.valueOf(category.toUpperCase());
            products = productService.findByCategory(categoryEnum);
        } catch (IllegalArgumentException e) {
            products = productService.findAll();
        }
    } else if (available != null) {
        products = productService.findByAvailability(available);
    } else {
        products = productService.findAll();
    }
    
    return ResponseEntity.ok(products);
}

    // List all Products
   
   

    // Read a Product
    
    

    // Update a Product
    
    

    // Delete a Product
   
    
}