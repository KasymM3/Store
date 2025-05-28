package com.example.shop.model;

import com.example.shop.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(length = 1000)
    private String description;

    private Double price;
    private String imageUrl;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) // чтобы не было рекурсии
    private User addedBy;

    public Product() {}

    public Product(String name, String desc, Double price, String imageUrl, String category, User addedBy) {
        this.name = name;
        this.description = desc;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.addedBy = addedBy;
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public User getAddedBy() { return addedBy; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCategory(String category) { this.category = category; }
    public void setAddedBy(User addedBy) { this.addedBy = addedBy; }
}
