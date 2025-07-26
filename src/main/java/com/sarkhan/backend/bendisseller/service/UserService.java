package com.sarkhan.backend.bendisseller.service;

import com.sarkhan.backend.bendisseller.dto.authorization.UserProfileRequest;
import com.sarkhan.backend.bendisseller.model.User;

public interface UserService {
    User save(User user);

    User updateUserProfile(UserProfileRequest userProfileRequest, String token);

    User getByEmail(String email);
}
