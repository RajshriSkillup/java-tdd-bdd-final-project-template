package com.productstore.service.factory;

import com.github.javafaker.Faker;
import com.productstore.service.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductFactory {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    
    /**
     * Creates a single product with random data
     * @return A randomly generated product
     */
    public static Product createProduct() {
        Product product = new Product();
        product.setId(random.nextLong(1000) + 1); // Random ID between 1-1000
        product.setName(generateProductName());
        product.setDescription(faker.lorem().paragraph(1));
        product.setPrice(generateRandomPrice());
        product.setAvailable(random.nextBoolean());
        product.setCategory(generateRandomCategory());
        return product;
    }
    
    /**
     * Creates a list of random products
     * @param count Number of products to create
     * @return List of randomly generated products
     */
    public static List<Product> createProductList(int count) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            products.add(createProduct());
        }
        return products;
    }
    
    /**
     * Generates a random product name
     * @return Random product name
     */
    private static String generateProductName() {
        String[] productOptions = {
            "Hat", "Pants", "Shirt", "Apple", "Banana", 
            "Pots", "Towels", "Ford", "Chevy", "Hammer", "Wrench"
        };
        return productOptions[random.nextInt(productOptions.length)];
    }
    
    /**
     * Generates a random price between 0.5 and 2000.0
     * @return Random price as BigDecimal
     */
    private static BigDecimal generateRandomPrice() {
        double amount = 0.5 + (2000.0 - 0.5) * random.nextDouble();
        // Round to 2 decimal places
        return BigDecimal.valueOf(Math.round(amount * 100.0) / 100.0);
    }
    
    /**
     * Generates a random product category
     * @return Random product category
     */
    private static Product.Category generateRandomCategory() {
        Product.Category[] categories = Product.Category.values();
        return categories[random.nextInt(categories.length)];
    }
}