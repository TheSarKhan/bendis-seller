package com.sarkhan.backend.bendisseller.controller;

import com.sarkhan.backend.bendisseller.dto.seller.SellerResponseDTO;
import com.sarkhan.backend.bendisseller.dto.seller.UpdateSellerRequestDto;
import com.sarkhan.backend.bendisseller.exception.DataNotFoundException;
import com.sarkhan.backend.bendisseller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> getAll() {
        return ResponseEntity.ok(sellerService.getAll());
    }

    @GetMapping("/myInfo")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<SellerResponseDTO> getMyInfo(@RequestHeader("Authorization") String token) throws DataNotFoundException, DataNotFoundException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return ResponseEntity.ok(sellerService.getByToken(token));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
    public ResponseEntity<SellerResponseDTO> update(@RequestBody UpdateSellerRequestDto updateSellerRequestDto,
                                                    @RequestHeader("Authorization") String token) throws DataNotFoundException {
        return ResponseEntity.ok(sellerService.update(updateSellerRequestDto, token));
    }
}
