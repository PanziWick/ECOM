package com.example.ecommercedemo.Services;

import com.example.ecommercedemo.DTO.ApiResponse;
import com.example.ecommercedemo.Models.Product;
import com.example.ecommercedemo.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByID(Long id){
        return productRepository.findById(id);
    }

    public ResponseEntity<ApiResponse<List<Product>>> addNewProducts (Product product){
        try {
            if(isProductDuplicate(product.getName())){
                return ResponseEntity.badRequest().body(new ApiResponse<>(400,"Product with same name already Exists",new ArrayList<>()));
            }
            productRepository.save(product);
            return ResponseEntity.ok(new ApiResponse<>(201,"Product Added",new ArrayList<>()));

        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"server error",new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Product>>> updateProduct(Product updatedproduct,Long id){
        try{
            Optional<Product> existingProductOptional = productRepository.findById(id);
            if(existingProductOptional.isPresent()){
                Product existingProduct = existingProductOptional.get();

                existingProduct.setName(updatedproduct.getName());
                existingProduct.setDescription(updatedproduct.getDescription());
                existingProduct.setPrice(updatedproduct.getPrice());

                productRepository.save(existingProduct);
                return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", new ArrayList<>()));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Product not found", new ArrayList<>()));
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500,"server error",new ArrayList<>()));
        }
    }

    public ResponseEntity<ApiResponse<List<Void>>> deleteProduct(Long id){
        try{
            Optional<Product> exisitingProductOptional = productRepository.findById(id);
            if(exisitingProductOptional.isPresent()){
                Product product = exisitingProductOptional.get();
                productRepository.deleteById(id);
                return ResponseEntity.ok(new ApiResponse<>(200, "Product Deleted successfully", new ArrayList<>()));
            }else{
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "Product not found", new ArrayList<>()));
            }

        }catch (Exception e){
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "An error occurred while deleting the product", new ArrayList<>()));
        }
    }

    private boolean isProductDuplicate(String productName) {
        return productRepository.existsByName(productName);
    }
}
