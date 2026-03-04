package com.example.productCatalog.config;


import com.example.productCatalog.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class AppConfig {
    @Bean
    public BlockingQueue<Product> productQueue() {
        return new LinkedBlockingQueue<>();
    }
}