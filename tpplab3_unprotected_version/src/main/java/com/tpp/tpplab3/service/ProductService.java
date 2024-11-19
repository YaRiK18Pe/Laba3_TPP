package com.tpp.tpplab3.service;

import com.tpp.tpplab3.models.Product;
import com.tpp.tpplab3.repository.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Product> getAllProducts() {
        return productsRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productsRepository.findById(id);
    }

    public void addProduct(Product product) {
        productsRepository.save(product);
    }

    public void updateProduct(Product product) {
        productsRepository.save(product);
    }

    public void deleteProduct(int id) {
        productsRepository.deleteById(id);
    }
}
