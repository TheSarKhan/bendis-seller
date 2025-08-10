package com.sarkhan.backend.bendisseller.dto.product;

import com.sarkhan.backend.bendisseller.dto.product.items.ColorAndSizeRequest;
import com.sarkhan.backend.bendisseller.model.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ProductRequest(@NotBlank String name,
                             @Min(0) BigDecimal originalPrice,
                             @Min(0) BigDecimal discountedPrice,
                             Long subCategoryId,
                             @JdbcTypeCode(SqlTypes.JSON)
                             List<ColorAndSizeRequest> colorAndSizeRequests,
                             Gender gender,
                             String description,
                             List<Long> pluses,
                             @JdbcTypeCode(SqlTypes.JSON)
                             Map<String, String> specifications) {
}