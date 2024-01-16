package com.example.ecommercedemo.Repositories;

import com.example.ecommercedemo.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
    boolean existsByName(String name);
}
