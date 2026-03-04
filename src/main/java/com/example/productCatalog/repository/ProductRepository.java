package com.example.productCatalog.repository;


import com.example.productCatalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
