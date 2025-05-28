
package com.example.shop.repository;

import com.example.shop.entity.ProductLike;
import com.example.shop.entity.ProductLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeKey> {

    
    int countByIdProductId(Long productId);

    
    boolean existsByIdUserIdAndIdProductId(Long userId, Long productId);

    
    void deleteByIdUserIdAndIdProductId(Long userId, Long productId);

    
    void deleteByIdProductId(Long productId);
}
