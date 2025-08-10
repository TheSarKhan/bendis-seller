package com.sarkhan.backend.bendisseller.dto.product.items;

import com.sarkhan.backend.bendisseller.model.enums.Color;

import java.util.Map;

public record ColorAndSizeRequest(Color color,
                                  Integer photoCount,
                                  Long stock,
                                  Map<String, Long> sizeStockMap) {
}
