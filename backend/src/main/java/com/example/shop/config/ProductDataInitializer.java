package com.example.shop.config;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductDataInitializer implements CommandLineRunner {
    private final ProductRepository repo;
    public ProductDataInitializer(ProductRepository repo) { this.repo = repo; }

    @Override
    public void run(String... args) throws Exception {












    }
}
