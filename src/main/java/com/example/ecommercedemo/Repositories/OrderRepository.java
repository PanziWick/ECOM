package com.example.ecommercedemo.Repositories;

import com.example.ecommercedemo.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
