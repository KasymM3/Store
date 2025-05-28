// back/src/main/java/com/example/shop/service/LikeServiceImpl.java
package com.example.shop.service;

import com.example.shop.entity.ProductLike;
import com.example.shop.entity.User;
import com.example.shop.repository.ProductLikeRepository;
import com.example.shop.service.LikeService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService {

    private final ProductLikeRepository likeRepo;
    private final UserService userService;
    private final ProductService productService;

    public LikeServiceImpl(ProductLikeRepository likeRepo,
                           UserService userService,
                           ProductService productService) {
        this.likeRepo = likeRepo;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    @Transactional
    public boolean toggleLike(Long userId, Long productId) {
        if (likeRepo.existsByIdUserIdAndIdProductId(userId, productId)) {
            likeRepo.deleteByIdUserIdAndIdProductId(userId, productId);
            return false;
        } else {
            User user = userService.findById(userId);
            var product = productService.getById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            likeRepo.save(new ProductLike(user, product));
            return true;
        }
    }

    @Override
    public int countLikes(Long productId) {
        return likeRepo.countByIdProductId(productId);
    }

    @Override
    public boolean isLikedByUser(Long userId, Long productId) {
        return likeRepo.existsByIdUserIdAndIdProductId(userId, productId);
    }
}
