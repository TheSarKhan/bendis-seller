package com.sarkhan.backend.bendisseller.dto.product;

import com.sarkhan.backend.bendisseller.model.product.Product;

import java.util.List;

public record ProductResponseForHomePage(List<Product> famousProducts,
                                         List<Product> discountedProducts,
                                         List<Product> mostFavoriteProducts,
                                         List<Product> flashProducts,
                                         List<Product> recommendedProducts) {
}
