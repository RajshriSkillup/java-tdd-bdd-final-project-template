package com.productstore.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ProductServiceApplication {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
        
        logger.info("*********************************************************************");
        logger.info("  P R O D U C T   S E R V I C E   R U N N I N G  ");
        logger.info("*********************************************************************");
        logger.info("Service initialized!");
    }
}