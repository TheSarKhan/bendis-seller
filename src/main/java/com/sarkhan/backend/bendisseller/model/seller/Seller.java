package com.sarkhan.backend.bendisseller.model.seller;

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
@Table(name = "sellers")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"password"})
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fullName;
    String brandName;
    @Column(unique = true)
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
