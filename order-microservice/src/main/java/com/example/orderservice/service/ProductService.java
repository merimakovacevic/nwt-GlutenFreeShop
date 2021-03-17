package com.example.orderservice.service;

import com.example.orderservice.model.Product;
import com.example.orderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void addNewProduct(Product product) {
        productRepository.save(product);
    }
}
