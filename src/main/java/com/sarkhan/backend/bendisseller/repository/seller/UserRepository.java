package com.sarkhan.backend.bendisseller.repository.seller;

import com.sarkhan.backend.bendisseller.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByBrandEmail(String email); // findByUsername -> findByEmail

    @Query("SELECT  u.fullName  FROM User u WHERE u.id= :userId")
    String findUserNameByUserId(Long userId);
}
