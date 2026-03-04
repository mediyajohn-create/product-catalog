package com.example.productCatalog.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 5, message = "Description must be at least 10 characters")
    private String description;

    @DecimalMin(value = "0.0", message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Category is required")
    private String category;
}
