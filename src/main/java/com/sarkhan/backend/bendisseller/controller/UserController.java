package com.sarkhan.backend.bendisseller.controller;

import com.sarkhan.backend.bendisseller.dto.authorization.TokenResponse;
import com.sarkhan.backend.bendisseller.dto.authorization.UserProfileRequest;
import com.sarkhan.backend.bendisseller.dto.seller.SellerRequestDTO;
import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;
import com.sarkhan.backend.bendisseller.jwt.JwtService;
import com.sarkhan.backend.bendisseller.model.User;
import com.sarkhan.backend.bendisseller.redis.RedisService;
import com.sarkhan.backend.bendisseller.service.SellerService;
import com.sarkhan.backend.bendisseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final SellerService sellerService;
    private final UserService userService;
    private final JwtService jwtService;
    private final RedisService redisService;

    ////-----

    @PostMapping("/create/seller")
    public ResponseEntity<?> createBrand(@RequestBody SellerRequestDTO sellerRequest,
                                         @RequestHeader("Authorization") String token) throws DataNotFoundException {
        token = token.substring(7);
        SellerResponseDTO seller = sellerService.createSeller(sellerRequest, token);
        return ResponseEntity.status(201).body(seller);
    }

    @PostMapping("/change/user/profile")
    public ResponseEntity<?> completeUserProfile(@RequestHeader("Authorization") String token, @RequestBody UserProfileRequest userProfileRequest) {
        token = token.substring(7);
      User user=userService.updateUserProfile(userProfileRequest, token);
      String accessToken=jwtService.generateAccessToken(user.getEmail(),null);
      String refreshToken=jwtService.generateRefreshToken(user.getEmail());
      redisService.deleteRefreshToken(user.getEmail());
      redisService.deleteTokenFromRedis(user.getEmail());
      redisService.saveTokenToRedis(accessToken,user.getEmail());
      redisService.saveRefreshToken(user.getEmail(),refreshToken,7);
    return ResponseEntity.status(201).body(TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build());
    }
}
