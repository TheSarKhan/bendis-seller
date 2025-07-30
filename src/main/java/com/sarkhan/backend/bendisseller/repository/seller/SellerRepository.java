package com.sarkhan.backend.bendisseller.repository.seller;

import com.sarkhan.backend.bendisseller.model.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByBrandEmail(String email); // findByUsername -> findByEmail

    @Query("SELECT  u.fullName  FROM Seller u WHERE u.id= :sellerId")
    String findBrandNameById(Long sellerId);
}
