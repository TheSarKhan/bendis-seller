package com.sarkhan.backend.bendisseller.dto.product;

import com.sarkhan.backend.bendisseller.model.enums.Color;

import java.util.List;

public record ProductResponseForSelectedSubCategoryAndComplexFilter(
        List<ProductResponseForGroupOfProduct> products,
        List<String> specifications,
        List<Color> colors,
        List<String> sizes,
        ProductFilterRequest selectedFilters) {
}