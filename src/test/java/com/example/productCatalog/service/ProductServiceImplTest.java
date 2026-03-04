package com.example.productCatalog.service;

import com.example.productCatalog.dto.ProductRequest;
import com.example.productCatalog.entity.Product;
import com.example.productCatalog.repository.ProductRepository;
import com.example.productCatalog.service.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private BlockingQueue<Product> productQueue;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetProductById_found() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .description("Gaming laptop")
                .price(1200.0)
                .category("Electronics")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertEquals("Laptop", result.getName());
        assertEquals(1200.0, result.getPrice());
        Mockito.verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testGetProductById_notFound() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(99L));
    }

    @Test
    void testSubmitProduct() {
        ProductRequest request = new ProductRequest();
        Product product = Product.builder()
                .id(1L)
                .name("Phone")
                .description("Smartphone")
                .price(800.0)
                .category("Electronics")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(product);

        Product saved = productService.submit(request);

        assertEquals("Phone", saved.getName());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Product.class));
        Mockito.verify(productQueue, Mockito.times(1)).add(saved);
    }

    @Test
    void testDeleteById_success() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);

        String result = productService.deleteById(1L);

        assertEquals("Delete success", result);
        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_notFound() {
        Mockito.when(repository.existsById(99L)).thenReturn(false);

        String result = productService.deleteById(99L);

        assertEquals("Product id not found", result);
        Mockito.verify(repository, Mockito.never()).deleteById(99L);
    }
}
