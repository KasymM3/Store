package com.example.shop.dto;

public class AddToCartRequest {
    private Long productId;
    private Integer quantity; // можно null → по умолчанию 1

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
