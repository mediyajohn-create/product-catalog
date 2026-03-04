package com.example.productCatalog.service;

import com.example.productCatalog.entity.Product;
import com.example.productCatalog.dto.ProductRequest;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ProductService {

    Product submit(ProductRequest request);

    String deleteById(Long pid);

    @Cacheable(value = "products", key = "#id")
    Product getProductById(Long id);
}
