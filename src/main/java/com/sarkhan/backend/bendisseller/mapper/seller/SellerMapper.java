package com.sarkhan.backend.bendisseller.mapper.seller;

import com.sarkhan.backend.bendisseller.dto.seller.SellerRequestDTO;
import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerResponseDTO sellerToSellerResponseDto(User seller);

    List<SellerResponseDTO> sellersToSellersResponseDto(List<User> sellers);

    User sellerRequestDtoToSeller(SellerRequestDTO sellerRequestDTO);

}
