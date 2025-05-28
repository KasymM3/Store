// back/src/main/java/com/example/shop/entity/ProductLike.java
package com.example.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_likes")
public class ProductLike {

    @EmbeddedId
    private ProductLikeKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private com.example.shop.model.Product product;

    public ProductLike() {}

    public ProductLike(User user, com.example.shop.model.Product product) {
        this.user = user;
        this.product = product;
        this.id = new ProductLikeKey(user.getId(), product.getId());
    }

    // геттеры/сеттеры
    public ProductLikeKey getId() { return id; }
    public void setId(ProductLikeKey id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public com.example.shop.model.Product getProduct() { return product; }
    public void setProduct(com.example.shop.model.Product product) { this.product = product; }
}
