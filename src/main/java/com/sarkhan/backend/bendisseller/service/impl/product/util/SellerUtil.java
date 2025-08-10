package com.sarkhan.backend.bendisseller.service.impl.product.util;

import com.sarkhan.backend.bendisseller.model.product.Product;
import com.sarkhan.backend.bendisseller.model.product.items.ProductUserHistory;
import com.sarkhan.backend.bendisseller.model.seller.Seller;
import com.sarkhan.backend.bendisseller.repository.product.items.ProductUserHistoryRepository;
import com.sarkhan.backend.bendisseller.service.SellerService;
import jakarta.security.auth.message.AuthException;
import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SellerUtil {

    public static Seller getCurrentSeller(SellerService userService, Logger log) throws AuthException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (email.equals("anonymousUser")) {
            log.warn("User doesn't login!!!");
            throw new AuthException("User doesn't login!!!");
        }
        return userService.getByBrandEmail(email);
    }

    public static void addProductUserHistory(Product product, SellerService userService, ProductUserHistoryRepository historyRepository, Logger log) {
        try {
            Seller user = getCurrentSeller(userService, log);
            ProductUserHistory history = ProductUserHistory.builder()
                    .userId(user.getId())
                    .subCategoryId(product.getSubCategoryId())
                    .build();
            historyRepository.save(history);
        } catch (AuthException ignored) {
        }
    }
}

