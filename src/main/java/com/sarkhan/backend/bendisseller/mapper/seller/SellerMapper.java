package com.sarkhan.backend.bendisseller.mapper.seller;

import com.sarkhan.backend.bendisseller.dto.seller.SellerRequestDTO;
import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.model.seller.Seller;
 import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerResponseDTO sellerToSellerResponseDto(Optional<Seller> seller);

    List<SellerResponseDTO> sellersToSellersResponseDto(List<Seller> sellers);

    Seller sellerRequestDtoToSeller(SellerRequestDTO sellerRequestDTO);

}
