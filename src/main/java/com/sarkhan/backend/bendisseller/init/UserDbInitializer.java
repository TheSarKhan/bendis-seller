package com.sarkhan.backend.bendisseller.init;

import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;
import com.sarkhan.backend.bendisseller.model.seller.Seller;
import com.sarkhan.backend.bendisseller.model.enums.Role;
import com.sarkhan.backend.bendisseller.service.SellerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbInitializer {
    private final SellerService userService;

    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() throws DataNotFoundException {
        Seller seller = Seller.builder()
                .fullName("Seller")
                .brandEmail("seller1234@gmail.com")
                .password(passwordEncoder.encode("Seller123"))
                .role(Role.SELLER)
                .build();

        if (!userService.existsByEmail(seller.getBrandEmail())) {
            SellerResponseDTO createdSeller = userService.createSeller(seller);
            log.info("User created: fullName={}, brandEmail={}, role={}",
                    createdSeller.fullName(),
                    createdSeller.brandEmail());
        } else {
            log.info("User with email {} already exists", seller.getBrandEmail());
        }
    }
}
