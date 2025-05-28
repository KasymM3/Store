
package com.example.shop.service;

public interface LikeService {
    /**
     * Переключает лайк (если уже лайк был — удаляет, иначе ставит).
     * @return true — после операции поставлен лайк; false — лайк удалён
     */
    boolean toggleLike(Long userId, Long productId);

    
    int countLikes(Long productId);

    
    boolean isLikedByUser(Long userId, Long productId);
}
