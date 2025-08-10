package com.sarkhan.backend.bendisseller.repository.product.items;

import com.sarkhan.backend.bendisseller.model.product.items.ProductUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUserHistoryRepository extends JpaRepository<ProductUserHistory, Long> {
    @Query("select subCategoryId from ProductUserHistory where userId = :userId group by subCategoryId order by COUNT(*) DESC")
    List<Long> findTopSubCategoryIdsByUserId(Long userId);
}
