package com.sarkhan.backend.bendisseller.service;


import com.sarkhan.backend.bendisseller.dto.authorization.LoginRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.RegisterRequest;
import com.sarkhan.backend.bendisseller.dto.authorization.TokenResponse;

public interface AuthenticationService {
     TokenResponse register(RegisterRequest request);
    TokenResponse login(LoginRequest request);

}
