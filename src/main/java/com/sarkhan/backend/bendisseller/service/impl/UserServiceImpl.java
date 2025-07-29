//package com.sarkhan.backend.bendisseller.service.impl;
//
//import com.sarkhan.backend.bendisseller.dto.authorization.UserProfileRequest;
//import com.sarkhan.backend.bendisseller.jwt.JwtService;
//import com.sarkhan.backend.bendisseller.model.user.User;
//import com.sarkhan.backend.bendisseller.model.enums.Gender;
//import com.sarkhan.backend.bendisseller.repository.seller.UserRepository;
//import com.sarkhan.backend.bendisseller.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//
//    @Override
//    public User save(User user) {
//        log.info("Someone try to create User.");
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User updateUserProfile(UserProfileRequest userProfileRequest, String token) {
//        Optional<User> user = userRepository.findByEmail(jwtService.extractEmail(token));
//        if (user.isPresent()) {
//            user.get().setBirthDate(userProfileRequest.getBirthDate());
//            user.get().setEmail(userProfileRequest.getEmail());
//            if (userProfileRequest.getGenderInt() == 0) {
//                user.get().setGender(Gender.MALE);
//            } else if (userProfileRequest.getGenderInt() == 1) {
//                user.get().setGender(Gender.FEMALE);
//            }
//            user.get().setCountryCode(userProfileRequest.getCountryCode());
//            user.get().setPhoneNumber(userProfileRequest.getPhoneNumber());
//            user.get().setFullName(userProfileRequest.getNameAndSurname());
//            userRepository.save(user.get());
//        }
//
//        return user.get();
//    }
//
//    @Override
//    public User getByEmail(String email) {
//        return userRepository.findByEmail(email).orElseThrow(() -> {
//            log.warn("Cannot find user with " + email + " email.");
//            return new NoSuchElementException("Cannot find user with " + email + " email.");
//        });
//    }
//}
