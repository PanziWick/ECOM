package com.example.ecommercedemo.Controller;

import com.example.ecommercedemo.DTO.ApiResponse;
import com.example.ecommercedemo.Models.Product;
import com.example.ecommercedemo.Repositories.ProductRepository;
import com.example.ecommercedemo.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse<>(200, "Products retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse<List<Product>>> createProduct(@RequestBody Product product) {
        try {
            return productService.addNewProducts(product);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<List<Product>>> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        try {
            return productService.updateProduct(product,id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<List<Void>>> deleteProduct(@PathVariable Long id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while retrieving the products", new ArrayList<>()));
        }
    }
}