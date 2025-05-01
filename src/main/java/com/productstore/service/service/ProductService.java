package com.productstore.service.service;

import com.productstore.service.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product update(Long id, Product productDetails);
    void delete(Long id);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByName(String name);
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByAvailability(Boolean available);
    List<Product> findByCategory(Product.Category category);
}
