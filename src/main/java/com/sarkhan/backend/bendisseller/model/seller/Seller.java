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
    @Column(name = "id")
    Long id;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "brand_name")
    String brandName;

    @Column(name = "brand_email", unique = true)
    String brandEmail;

    @Column(name = "brand_voen")
    String brandVOEN;

    @Column(name = "father_name")
    String fatherName;

    @Column(name = "fin_code")
    String finCode;

    @Column(name = "brand_phone")
    String brandPhone;

    @Column(name = "password")
    String password;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;
}
