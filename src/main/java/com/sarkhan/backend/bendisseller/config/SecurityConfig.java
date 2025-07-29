package com.sarkhan.backend.bendisseller.config;

import com.sarkhan.backend.bendisseller.handler.CustomAccessDeniedHandler;
import com.sarkhan.backend.bendisseller.handler.CustomAuthenticationEntryPoint;
import com.sarkhan.backend.bendisseller.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint)
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/api/v1/stories/**", "/api/v1/stories/like/story-id",
                                "/api/v1/auth/google-login", "/login", "/api/v1/auth/email/consultation",
                                "/api/v1/auth/email/appeal", "/api/v1/auth/**", "/api/v1/auth/refresh",
                                "/api/v1/home", "/api/v1/product", "/api/v1/product/famous",
                                "/api/v1/product/discounted", "/api/v1/product/most-favorite",
                                "/api/v1/product/flush", "/api/v1/product/recommended",
                                "/api/v1/product/id/**", "/api/v1/product/slug/**",
                                "/api/v1/product/name/**", "/api/v1/product/sub-category/**",
                                "/api/v1/product/seller/**", "/api/v1/product/filter"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/seller/myInfo").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/seller").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/seller").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("/api/v1/seller/dashboard/**").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/seller").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider);

        return http.build();
    }
}
