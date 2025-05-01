package com.productstore.service.repository;

import com.productstore.service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find products by name
    List<Product> findByName(String name);
    
    // Find products by price
    List<Product> findByPrice(BigDecimal price);
    
    // Find products by availability
    List<Product> findByAvailable(Boolean available);
    
    // Find products by category
    List<Product> findByCategory(Product.Category category);
}