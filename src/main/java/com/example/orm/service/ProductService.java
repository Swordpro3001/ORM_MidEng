package com.example.orm.service;

import com.example.orm.model.Product;
import com.example.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product getProductById(String id) {
        return repository.findByProductID(id);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        repository.deleteById(id);
    }
}

