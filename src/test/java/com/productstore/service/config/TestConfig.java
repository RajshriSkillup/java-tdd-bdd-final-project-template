package com.productstore.service.config;

import com.productstore.service.service.ProductService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public ProductService productService() {
        return Mockito.mock(ProductService.class);
    }
}
