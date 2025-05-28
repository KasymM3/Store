// back/src/main/java/com/example/shop/service/ProductService.java
package com.example.shop.service;

import com.example.shop.dto.ProductDto;
import com.example.shop.entity.User;
import com.example.shop.model.Product;
import com.example.shop.repository.ProductLikeRepository;
import com.example.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final ProductLikeRepository likeRepo;

    public ProductService(ProductRepository repo,
                          ProductLikeRepository likeRepo) {
        this.repo     = repo;
        this.likeRepo = likeRepo;
    }

    /* ---------- API ---------- */

    public List<String> getAllCategories() {
        return repo.findDistinctCategories();
    }

    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    public List<Product> getFiltered(String q, String sortKey) {
        List<Product> list = repo.findAll();

        /* фильтр по поисковой строке */
        if (q != null && !q.isBlank()) {
            String lower = q.toLowerCase();
            list = list.stream()
                    .filter(p ->
                            (p.getName()        != null && p.getName().toLowerCase().contains(lower)) ||
                                    (p.getDescription() != null && p.getDescription().toLowerCase().contains(lower))
                    )
                    .collect(Collectors.toList());
        }

        /* сортировка */
        if (sortKey != null && !sortKey.isBlank()) {
            switch (sortKey) {
                case "priceAsc"  -> list.sort(Comparator.comparing(Product::getPrice));
                case "priceDesc" -> list.sort(Comparator.comparing(Product::getPrice).reversed());
                case "nameAsc"   -> list.sort(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER));
                case "nameDesc"  -> list.sort(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER).reversed());
            }
        }
        return list;
    }

    @Transactional
    public void delete(Long id) {
        /* 1. Удаляем все лайки, связанные с товаром */
        likeRepo.deleteByIdProductId(id);

        /* 2. Удаляем сам товар */
        repo.deleteById(id);
    }

    public Product createProduct(Product product, User user) {
        product.setAddedBy(user);
        return repo.save(product);
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    /* ---------- DTO-маппер ---------- */
    public ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setCategory(p.getCategory());
        dto.setImageUrl(p.getImageUrl());

        if (p.getAddedBy() != null) {
            ProductDto.AddedByDto ab = new ProductDto.AddedByDto();
            ab.setId(p.getAddedBy().getId());
            ab.setName(p.getAddedBy().getName());
            dto.setAddedBy(ab);
        }
        return dto;
    }
}
