package com.productstore.service.service;

import com.productstore.service.model.Product;
import com.productstore.service.repository.ProductRepository;
import com.productstore.service.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a new product
    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    // Update an existing product
    @Override
    public Product update(Long id, Product productDetails) {
        Product product = findById(id);
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setAvailable(productDetails.getAvailable());
        product.setCategory(productDetails.getCategory());
        
        return productRepository.save(product);
    }

    // Delete a product
    @Override
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    // Find a product by ID
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    // List all products
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Find products by name
    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    // Find products by price
    @Override
    public List<Product> findByPrice(BigDecimal price) {
        return productRepository.findByPrice(price);
    }

    // Find products by availability
    @Override
    public List<Product> findByAvailability(Boolean available) {
        return productRepository.findByAvailable(available);
    }

    // Find products by category
    @Override
    public List<Product> findByCategory(Product.Category category) {
        return productRepository.findByCategory(category);
    }
}
