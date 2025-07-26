package com.sarkhan.backend.bendisseller.repository;

import com.sarkhan.backend.bendisseller.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByBrandEmail(String brandEmail);
}
