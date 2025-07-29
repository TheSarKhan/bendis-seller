package com.sarkhan.backend.bendisseller.controller;

import com.sarkhan.backend.bendisseller.dto.authorization.LoginRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.RegisterRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.TokenResponse;
import com.sarkhan.backend.bendisseller.jwt.JwtService;
import com.sarkhan.backend.bendisseller.model.user.User;
import com.sarkhan.backend.bendisseller.redis.RedisService;
import com.sarkhan.backend.bendisseller.repository.seller.UserRepository;
import com.sarkhan.backend.bendisseller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest dto) {

        TokenResponse tokenResponse = authenticationService.register(dto);
        return ResponseEntity.status(200).body(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByBrandEmail(request.getEmail());
        TokenResponse tokenResponse = authenticationService.login(request);
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.status(400).body("Email or Password is null");
        }
        if (user.isEmpty()) {
            return ResponseEntity.status(400).body("Email does not exist");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(400).body("Passwords do not match");
        }
        return ResponseEntity.status(200).body(tokenResponse);
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        String email = jwtService.extractEmail(refreshToken);

        if (email.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        // 2. Redis'teki refresh token ile eşleşiyor mu kontrol et
        String storedRefreshToken = redisService.getRefreshToken(email);
        System.out.println(storedRefreshToken + "Stored Refresh Token");
        System.out.println(refreshToken + "Current Refresh token");
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token mismatch or expired");
        }

        // 3. Yeni access token üret
        String newAccessToken = jwtService.generateAccessToken(email, null);

        // 4. (Opsiyonel) Yeni refresh token üretip Redis'i güncelle
        String newRefreshToken = jwtService.generateRefreshToken(email);
        redisService.deleteRefreshToken(email);
        redisService.saveRefreshToken(email, newRefreshToken, 7); // 7 gün geçerli

        // 5. Yeni token'ları döndür
        return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
    }

}
