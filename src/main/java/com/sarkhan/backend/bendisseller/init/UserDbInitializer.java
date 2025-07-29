package com.sarkhan.backend.bendisseller.init;

import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;
import com.sarkhan.backend.bendisseller.model.user.User;
import com.sarkhan.backend.bendisseller.model.enums.Role;
import com.sarkhan.backend.bendisseller.service.SellerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbInitializer {
    private final SellerService userService;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws DataNotFoundException {
        User admin = User.builder()
                .fullName("Admin")
                .brandEmail("admin1234@gmail.com")
                .password(passwordEncoder.encode("Admin123"))
                .role(Role.ADMIN)
                .build();

        log.info("User created :" + userService.createSeller(admin));
    }
}
