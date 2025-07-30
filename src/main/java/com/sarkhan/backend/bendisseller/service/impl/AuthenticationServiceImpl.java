package com.sarkhan.backend.bendisseller.service.impl;

import com.sarkhan.backend.bendisseller.dto.authorization.LoginRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.RegisterRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.TokenResponse;
import com.sarkhan.backend.bendisseller.jwt.JwtService;
import com.sarkhan.backend.bendisseller.model.seller.Seller;
 import com.sarkhan.backend.bendisseller.model.enums.Role;
import com.sarkhan.backend.bendisseller.redis.RedisService;
import com.sarkhan.backend.bendisseller.repository.seller.SellerRepository;
 import com.sarkhan.backend.bendisseller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Override
    public TokenResponse register(RegisterRequest request) {
        if (sellerRepository.findByBrandEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email artıq qeydiyyatdan keçib!");
        }
        LocalDateTime now = LocalDateTime.now();
        String refreshToken = jwtService.generateRefreshToken(request.getEmail());
        redisService.saveRefreshToken(request.getEmail(), refreshToken, 7);
        Seller user = new Seller();
        user.setRole(Role.SELLER);
        user.setFullName(request.getFullName());
        user.setBrandEmail(request.getEmail());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        sellerRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",user.getRole());
        claims.put("userId",user.getId());
        String accessToken = jwtService.generateAccessToken(request.getEmail(), claims);

        redisService.saveTokenToRedis(accessToken, request.getEmail());

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        Optional<Seller> sellerOptional = sellerRepository.findByBrandEmail(request.getEmail());

        if (sellerOptional.isEmpty()) {
            throw new RuntimeException("Email tapılmadı və ya səhvdir");
        }

        Seller seller = sellerOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), seller.getPassword())) {
            throw new RuntimeException("Yanlış şifrə");
        }

        String accessToken = jwtService.generateAccessToken(seller.getBrandEmail(), null);
        String refreshToken = jwtService.generateRefreshToken(seller.getBrandEmail());

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

}
