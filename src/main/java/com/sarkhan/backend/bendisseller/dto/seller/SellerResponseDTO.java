package com.sarkhan.backend.bendisseller.dto.seller;

public record SellerResponseDTO(Long id,String fullName, String brandName,
                                String brandEmail, String brandVOEN, String fatherName,
                                String finCode, String brandPhone) {
}
