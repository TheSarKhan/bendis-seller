package com.sarkhan.backend.bendisseller.service.impl;


import com.sarkhan.backend.bendisseller.model.seller.Seller;
import com.sarkhan.backend.bendisseller.model.seller.SellerUserDetails;
import com.sarkhan.backend.bendisseller.repository.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerUserDetailsService implements UserDetailsService {

    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByBrandEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found with email: " + email));
        return new SellerUserDetails(seller);
    }
}
