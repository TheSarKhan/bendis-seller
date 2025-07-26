package com.sarkhan.backend.bendisseller.service.impl;

import com.sarkhan.backend.bendisseller.dto.authorization.LoginRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.RegisterRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.TokenResponse;
import com.sarkhan.backend.bendisseller.jwt.JwtService;
import com.sarkhan.backend.bendisseller.model.User;
import com.sarkhan.backend.bendisseller.model.enums.Role;
import com.sarkhan.backend.bendisseller.redis.RedisService;
import com.sarkhan.backend.bendisseller.repository.UserRepository;
import com.sarkhan.backend.bendisseller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    @Override
    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email artıq qeydiyyatdan keçib!");
        }
        LocalDateTime now = LocalDateTime.now();
        String refreshToken = jwtService.generateRefreshToken(request.getEmail());
        redisService.saveRefreshToken(request.getEmail(), refreshToken, 7);
        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
      //  user.setRoles(roles);
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(request.getEmail(), null); // Claims kısmı null olabilir

        redisService.saveTokenToRedis(accessToken, request.getEmail());

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email tapılmadı və ya səhvdir");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Yanlış şifrə");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail(), null);
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

}
