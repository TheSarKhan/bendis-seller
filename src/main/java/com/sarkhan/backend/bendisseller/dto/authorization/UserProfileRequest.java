package com.sarkhan.backend.bendisseller.dto.authorization;

import com.sarkhan.backend.bendisseller.model.BirthDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileRequest {
    String nameAndSurname;
    String email;
    byte genderInt;
    String countryCode;
    String phoneNumber;
    BirthDate birthDate;
}
