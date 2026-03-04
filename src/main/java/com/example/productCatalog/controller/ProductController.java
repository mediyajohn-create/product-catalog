package com.example.productCatalog.controller;

import com.example.productCatalog.entity.Product;
import jakarta.validation.Valid;
import com.example.productCatalog.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.productCatalog.service.ProductService;

import java.util.List;


@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    // JSON only
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> submit(
            @Valid @RequestBody ProductRequest request) {

        Product product = service.submit(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }
}