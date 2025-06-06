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
    
    
}