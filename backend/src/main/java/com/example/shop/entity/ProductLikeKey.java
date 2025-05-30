
package com.example.shop.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductLikeKey implements Serializable {

    private Long userId;
    private Long productId;

    public ProductLikeKey() {}

    public ProductLikeKey(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }


    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductLikeKey)) return false;
        ProductLikeKey that = (ProductLikeKey) o;
        return Objects.equals(userId, that.userId)
                && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
