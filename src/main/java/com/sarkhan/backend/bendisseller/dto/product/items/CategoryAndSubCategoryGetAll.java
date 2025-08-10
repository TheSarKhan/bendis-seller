package com.sarkhan.backend.bendisseller.dto.product.items;

import com.sarkhan.backend.bendisseller.model.product.items.Category;
import com.sarkhan.backend.bendisseller.model.product.items.SubCategory;

import java.util.List;

public record CategoryAndSubCategoryGetAll(List<Category> categories,
                                           List<SubCategory> subCategories) {
}
