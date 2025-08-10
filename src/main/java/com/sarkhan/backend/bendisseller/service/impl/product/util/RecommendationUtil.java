package com.sarkhan.backend.bendisseller.service.impl.product.util;

import com.sarkhan.backend.bendisseller.model.product.Product;
import com.sarkhan.backend.bendisseller.model.product.items.SubCategory;
import com.sarkhan.backend.bendisseller.model.seller.Seller;
import com.sarkhan.backend.bendisseller.repository.product.ProductRepository;
import com.sarkhan.backend.bendisseller.repository.product.items.ProductUserHistoryRepository;
import com.sarkhan.backend.bendisseller.service.SellerService;
import com.sarkhan.backend.bendisseller.service.product.items.SubCategoryService;
import jakarta.security.auth.message.AuthException;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

import static com.sarkhan.backend.bendisseller.service.impl.product.util.SellerUtil.getCurrentSeller;

public class RecommendationUtil {
    static Random random = new Random();

    public static List<Product> getRecommendedProduct(
            ProductUserHistoryRepository historyRepository,
            ProductRepository productRepository,
            SubCategoryService subCategoryService,
            SellerService userService,
            Logger log,
            int recommendedProductMaxSize,
            double shuffleProbability,
            int maxSwapDistance) {

        Seller seller;
        try {
            seller = getCurrentSeller(userService, log);
        } catch (AuthException ignored) {
            List<Product> recommendedProducts = productRepository.
                    findAll(PageRequest.of(1,
                            recommendedProductMaxSize,
                            Sort.by("createAt"))).
                    toList();
            log.info("recommended products: " + recommendedProducts);
            return recommendedProducts;
        }

        Set<Product> products = new LinkedHashSet<>();
        List<Long> subCategoryIds = historyRepository.findTopSubCategoryIdsByUserId(seller.getId());

        for (Long subCategoryId : subCategoryIds) {
            if (random.nextDouble() < 0.8) {
                products.addAll(productRepository.getBySubCategoryId(subCategoryId)
                        .stream()
                        .limit(recommendedProductMaxSize)
                        .collect(Collectors.toSet()));
            }
        }

        if (products.size() < recommendedProductMaxSize) {
            List<Long> categoryIds = subCategoryService.getCategoryIdsBySubCategoryIds(subCategoryIds);
            for (Long categoryId : categoryIds) {
                for (SubCategory subCategory : subCategoryService.getByCategoryId(categoryId)) {
                    products.addAll(productRepository.getBySubCategoryId(subCategory.getId()));
                    if (products.size() >= recommendedProductMaxSize) break;
                }
                if (products.size() >= recommendedProductMaxSize) break;
            }
        }

        return partialShuffle(products.stream().toList(), shuffleProbability, maxSwapDistance);
    }

    public static List<Product> partialShuffle(List<Product> input, double shuffleProbability, int maxSwapDistance) {
        List<Product> result = new ArrayList<>(input);

        for (int i = 0; i < result.size(); i++) {
            if (random.nextDouble() < shuffleProbability) {
                int swapWith = i + random.nextInt(Math.min(maxSwapDistance, result.size() - i));
                Collections.swap(result, i, swapWith);
            }
        }

        return result;
    }
}

