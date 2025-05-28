// back/src/main/java/com/example/shop/service/LikeService.java
package com.example.shop.service;

public interface LikeService {
    /**
     * Переключает лайк (если уже лайк был — удаляет, иначе ставит).
     * @return true — после операции поставлен лайк; false — лайк удалён
     */
    boolean toggleLike(Long userId, Long productId);

    /** Возвращает текущее кол-во лайков у товара */
    int countLikes(Long productId);

    /** Проверяет, лайкнул ли пользователь товар */
    boolean isLikedByUser(Long userId, Long productId);
}
