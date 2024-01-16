package com.example.ecommercedemo.Repositories;

import com.example.ecommercedemo.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
