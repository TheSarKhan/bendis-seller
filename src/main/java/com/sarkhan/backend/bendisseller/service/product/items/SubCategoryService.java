package com.sarkhan.backend.bendisseller.service.product.items;


import com.sarkhan.backend.bendisseller.dto.product.items.SubCategoryRequest;
import com.sarkhan.backend.bendisseller.model.product.items.SubCategory;

import java.util.List;

public interface SubCategoryService {
    SubCategory add(SubCategoryRequest request);

    List<SubCategory> getAll();

    SubCategory getById(Long id);

    SubCategory getByName(String name);

    List<SubCategory> searchByName(String name);

    List<SubCategory> getByCategoryId(Long categoryId);

    SubCategory update(Long id, SubCategoryRequest request);

    void delete(Long id);

    List<Long> getCategoryIdsBySubCategoryIds(List<Long> subCategoryIds);

    List<SubCategory> getByCategoryIds(List<Long> categoryIds);
}
