package com.sarkhan.backend.bendisseller.service;



import com.sarkhan.backend.bendisseller.dto.seller.SellerRequestDTO;
import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.dto.seller.UpdateSellerRequestDto;
import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;

import java.util.List;

public interface SellerService {
    SellerResponseDTO createSeller(SellerRequestDTO sellerRequestDTO, String token) throws DataNotFoundException;

    List<SellerResponseDTO> getAll();

    SellerResponseDTO getByToken(String token) throws DataNotFoundException;

    SellerResponseDTO update(UpdateSellerRequestDto updateSellerRequestDto, String token) throws DataNotFoundException;
}
