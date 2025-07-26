package com.sarkhan.backend.bendisseller.dto.authorization;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
     String fullName;
     String email;
     String password;
}
