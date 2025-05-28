package com.example.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private String category;
    private String description;
    private String imageUrl;

    public AddedByDto getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(AddedByDto addedBy) {
        this.addedBy = addedBy;
    }

    private AddedByDto addedBy;

    // ★ NEW
    @Data
    public static class AddedByDto {
        private Long id;
        private String name;
    }
    public ProductDto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
