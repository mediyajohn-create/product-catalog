package com.example.productCatalog.service.impl;

import com.example.productCatalog.config.AppConfig;
import com.example.productCatalog.dto.ProductRequest;
import com.example.productCatalog.entity.Product;
import com.example.productCatalog.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.productCatalog.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    // Use a shared queue that the Scheduler can also see
    private final BlockingQueue<Product> productQueue;

    public ProductServiceImpl(ProductRepository repository, BlockingQueue<Product> productQueue) {
        this.repository = repository;
        this.productQueue = productQueue;
    }


    @Override
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        System.out.println("Fetching from DB..."); // proves cache works
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }


    @Override
    @Transactional
    public Product submit(ProductRequest request) {
        // Mapping DTO to Entity
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        // 1. Save to Database immediately
        Product savedProduct = repository.save(product);

        // 2. Add to Queue for the background HTTPS task
        productQueue.add(savedProduct);

        return savedProduct;
    }

    @Override
    public String deleteById(Long pid) {
        if (repository.existsById(pid)) {
            repository.deleteById(pid);
            return "Delete success";
        } else {
            return "Product id not found";
        }
    }


}