package com.example.orm.repository;

import com.example.orm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByProductID(String productID);
}