package com.sarkhan.backend.bendisseller.model.user;

import com.sarkhan.backend.bendisseller.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class
User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String fullName;
    String brandName;
    String brandEmail;
    String brandVOEN;
    String fatherName;
    String finCode;
    String brandPhone;
    String password;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    Role role;

}
