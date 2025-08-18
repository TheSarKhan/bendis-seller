package com.sarkhan.backend.bendisseller.jwt;

import com.sarkhan.backend.bendisseller.model.seller.SellerUserDetails;
import com.sarkhan.backend.bendisseller.service.impl.SellerUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final @Lazy SellerUserDetailsService sellerUserDetailsService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.startsWith("/api/v1/auth/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        String email;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtService.extractEmail(jwt);
        } else {
            email = null;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(jwt, email)) {
                var sellerDetails = sellerUserDetailsService.loadUserByUsername(email);
                var authorities = sellerDetails.getAuthorities();
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(sellerDetails, null, authorities)
                );
                System.out.println("Token is valid for user: " + email);
            } else {
                System.out.println("Token is not valid for user: " + email);
            }
        } else {
            System.out.println("Email is null or already authenticated: " + email);
        }
        System.out.println("Authorization header: " + authorizationHeader);
        System.out.println("JWT token: " + jwt);
        System.out.println("Extracted email: " + email);


        filterChain.doFilter(request, response);
    }
}