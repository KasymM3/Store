package com.example.shop.controller;

import com.example.shop.dto.ProductDto;
import com.example.shop.entity.User;
import com.example.shop.model.Product;
import com.example.shop.service.LikeService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;  // ← импорт
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private final ProductService productService;
    private final UserService userService;
    private final LikeService likeService;

    @Autowired
    public ProductRestController(ProductService productService, UserService userService, LikeService likeService) {
        this.productService = productService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        List<ProductDto> dtos = productService.getFiltered(q, sort).stream()
                .map(productService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return productService.getById(id)
                .map(productService::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")  // ← только для аутентифицированных USER или ADMIN
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody Product product,
            Principal principal
    ) {
        User currentUser = userService.findByEmail(principal.getName());
        Product savedProduct = productService.createProduct(product, currentUser);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // ← только ADMIN
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @Valid @RequestBody Product input
    ) {
        return productService.getById(id)
                .map(existing -> {
                    existing.setName(input.getName());
                    existing.setPrice(input.getPrice());
                    existing.setCategory(input.getCategory());
                    existing.setDescription(input.getDescription());
                    existing.setImageUrl(input.getImageUrl());
                    productService.save(existing);
                    return ResponseEntity.ok(existing);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // ← только ADMIN
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productService.getById(id).isPresent()) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/likes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String,Object>> getLikes(
            @PathVariable Long id,
            Principal principal
    ) {
        User current = userService.findByEmail(principal.getName());
        boolean liked = likeService.isLikedByUser(current.getId(), id);
        int count  = likeService.countLikes(id);
        return ResponseEntity.ok(Map.of("liked", liked, "count", count));
    }

    @PostMapping("/{id}/like")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Map<String,Object>> toggleLike(
            @PathVariable Long id,
            Principal principal
    ) {
        User current = userService.findByEmail(principal.getName());
        boolean nowLiked = likeService.toggleLike(current.getId(), id);
        int count = likeService.countLikes(id);
        return ResponseEntity.ok(Map.of("liked", nowLiked, "count", count));
    }
}
