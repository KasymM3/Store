// back/src/main/java/com/example/shop/repository/ProductLikeRepository.java
package com.example.shop.repository;

import com.example.shop.entity.ProductLike;
import com.example.shop.entity.ProductLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeKey> {

    /**-- Сколько лайков у товара --**/
    int countByIdProductId(Long productId);

    /**-- Проверка «лайкнул ли пользователь товар» --**/
    boolean existsByIdUserIdAndIdProductId(Long userId, Long productId);

    /**-- Снять лайк конкретного пользователя --**/
    void deleteByIdUserIdAndIdProductId(Long userId, Long productId);

    /**-- Удалить все лайки конкретного товара (используем при удалении товара) --**/
    void deleteByIdProductId(Long productId);
}
