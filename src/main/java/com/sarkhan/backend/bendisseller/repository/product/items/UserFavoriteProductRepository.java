package com.sarkhan.backend.bendisseller.repository.product.items;

import com.sarkhan.backend.bendisseller.model.product.items.UserFavoriteProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavoriteProductRepository extends JpaRepository<UserFavoriteProduct, Long> {
    @Query("from UserFavoriteProduct where productId = :productId and userId = :userId")
    Optional<UserFavoriteProduct> getByProductIdAndUserId(Long productId, Long userId);
}
