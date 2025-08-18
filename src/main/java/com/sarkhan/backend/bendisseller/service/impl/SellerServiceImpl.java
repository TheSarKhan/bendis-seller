package com.sarkhan.backend.bendisseller.service.impl;

import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.dto.seller.UpdateSellerRequestDto;
import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;
import com.sarkhan.backend.bendisseller.jwt.JwtService;
import com.sarkhan.backend.bendisseller.mapper.seller.SellerMapper;

import com.sarkhan.backend.bendisseller.model.seller.Seller;
import com.sarkhan.backend.bendisseller.repository.seller.SellerRepository;
import com.sarkhan.backend.bendisseller.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final JwtService jwtService;
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;

    public List<SellerResponseDTO> getAll() {
        return sellerMapper.sellersToSellersResponseDto(sellerRepository.findAll());
    }

    @Override
    public SellerResponseDTO getByToken(String token) throws DataNotFoundException {
        String email = jwtService.extractEmail(token);
        Optional<Seller> seller = Optional.ofNullable(sellerRepository.findByBrandEmail(email).orElseThrow(() -> {
            log.error("Seller not found {}", email);
            return new DataNotFoundException("Seller not found");
        }));
        return sellerMapper.sellerToSellerResponseDto(seller);
    }

    @Override
    public SellerResponseDTO createSeller(Seller seller) throws DataNotFoundException {
        seller.setCreatedAt(LocalDateTime.now());
        seller.setUpdatedAt(LocalDateTime.now());
        sellerRepository.save(seller);
        return sellerMapper.sellerToSellerResponseDto(Optional.of(seller));
    }

    @Override
    public SellerResponseDTO update(UpdateSellerRequestDto updateSellerRequestDto, String token) throws DataNotFoundException {
        String email = jwtService.extractEmail(token);
        Seller seller = sellerRepository.findByBrandEmail(email).orElseThrow(() -> {
            log.error("User not found");
            return new DataNotFoundException("User not found");
        });
        seller.setFullName(updateSellerRequestDto.fullName());
        seller.setBrandEmail(updateSellerRequestDto.brandEmail());
        seller.setBrandName(updateSellerRequestDto.brandName());
        seller.setFatherName(updateSellerRequestDto.fatherName());
        seller.setBrandVOEN(updateSellerRequestDto.brandVOEN());
        seller.setBrandPhone(updateSellerRequestDto.brandPhone());
        seller.setFinCode(updateSellerRequestDto.finCode());
        sellerRepository.save(seller);
        return sellerMapper.sellerToSellerResponseDto(Optional.of(seller));
    }

    @Override
    public Seller getByBrandEmail(String email) {
        return sellerRepository.findByBrandEmail(email).orElseThrow(() -> {
            log.warn("Cannot find user with " + email + " email.");
            return new NoSuchElementException("Cannot find user with " + email + " email.");
        });
    }

    @Override
    public boolean existsByEmail(String brandEmail) {
        return sellerRepository.findByBrandEmail(brandEmail).isPresent();
    }
}
